package org.csid.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.csid.domain.*;
import org.csid.repository.*;
import org.csid.service.ISchoolLifeService;
import org.csid.service.ISchoolReportService;
import org.csid.service.SchoolReportService;
import org.csid.service.dto.*;
import org.csid.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@Service("ISchoolReportService")
@Transactional
public class SchoolReportServiceImpl_ implements ISchoolReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolReportServiceImpl_.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private SchoolReportService schoolReportService;

    @Autowired
    private SchoolReportMapper schoolReportMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private SchoolReportRepository schoolReportRepository;

    @Autowired
    private AssignmentModuleRepository assignmentModuleRepository;

    @Autowired
    private ISchoolLifeService schoolLifeService;

    @Autowired
    private AssignmentManagerRepository assignmentManagerRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private ClassroomMapper classroomMapper;

    @Autowired
    private AssignmentYearPeriodRepository assignmentYearPeriodRepository;

    /**
     * Generate School Report in pdf and returns it
     * @return File : School Report Generated
     */
    @Override
    public File generateSchoolReport(final Long accountCode) throws Exception {
        File pdfFile;
        FileOutputStream fos = null;

        try {
            final User user = userRepository.findOne(accountCode);

            final Document document = new Document(PageSize.A4, 20, 20, 20, 0);

            // create the file in memory
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();

            fillSchoolReport(user, document);

            document.close();

            pdfFile = File.createTempFile("bulletin", ".pdf");

            fos = new FileOutputStream(pdfFile);
            fos.write(baos.toByteArray());

        } catch (FileNotFoundException e) {
            LOGGER.error("File Not Found " + e.getMessage());
            throw new Exception("File Not Found");
        } catch (DocumentException e) {
            LOGGER.error("Error during creating document " + e.getMessage());
            throw new Exception("Error during creating document");
        } catch (IOException e) {
            LOGGER.error("Error during creating file " + e.getMessage());
            throw new Exception("Error during creating file" +
                "");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pdfFile;
    }

    private void fillSchoolReport(User user, Document document) throws Exception {
        final LocalDate currentDate = now();

        final Student student = studentRepository.findStudentByUser(user);
        final List<Inscription> inscriptions = inscriptionRepository.findAllByCurrentSchoolYear(currentDate);

        final SchoolReport schoolReport = this.schoolReportRepository.getSchoolReportByStudentWhereYearPeriodMax(student.getId());

        final Set<Evaluation> evaluations = schoolReport.getEvaluations();

        final String firstNameManager = schoolReport.getManager().getUser().getFirstName();
        final String lastNameManager =  schoolReport.getManager().getUser().getLastName();

        List<School> schools = this.getSchoolsByManager(schoolReport.getManager().getUser().getId())
            .stream()
            .map(s -> schoolMapper.toEntity(s))
            .collect(Collectors.toList());

        PdfPCell cell = new PdfPCell();
        Font fontTitle = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
        Font fontContent = new Font(FontFamily.HELVETICA, 10);
        Font fontComment = new Font(FontFamily.HELVETICA, 6);

        Paragraph paragraph;//Bloc 1 : School & Student --------------------------------------------------------
        PdfPTable table = initPdfPTable(9, 100);

        for (Inscription inscription : inscriptions) {
            for (Student s : inscription.getStudents()) {
                if (s.equals(student)) {
                    table.addCell(getPdfPCellCustomized(inscription.getSchool().getWording() + "\n"+ inscription.getSchool().getAddress() + "\n" + inscription.getSchool().getPostalCode() + " " +  inscription.getSchool().getCity() + "\n" + (schools.get(0).getPhoneNumber() != null ? schools.get(0).getPhoneNumber() : ""), null, 4, 5.0f, "left", true));
                    table.addCell(getPdfPCellCustomized("Nom - Prénom : " + user.getLastName() + " " + user.getFirstName() + "\nNé(e) le : " + student.getDateOfBirth() + "\nClasse : " +  inscription.getClassroom().getEntitled() + " " + (inscription.getClassroom().getOption() != null ? inscription.getClassroom().getOption() : "") + "\nAnnée scolaire : " + inscription.getSchoolYear().getStartDate().getYear() + " - " +  inscription.getSchoolYear().getEndDate().getYear() + "", null, 4, 5.0f, "left", true));
                    break;
                }
            }

        }

        //QR code
        final Image img = Image.getInstance(this.generateQrCode());
        cell.setColspan(1);
        cell.setPadding(5.0f);
        table.addCell(img);
        document.add(table);

        //Entitled period
        paragraph = new Paragraph("Bulletin du " + schoolReport.getYearPeriod().getEntitled() + "\n\n", fontTitle);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        //Bloc 2 Moyennes ------------------------------------------------------------------
        table = initPdfPTable(9, 100);

        //Entilted Moyennes
        table.addCell(getPdfPCellCustomized("Matière\n\nNom du professeur", fontContent, 3, 5.0f, "left", true));
        table.addCell(getPdfPCellCustomized("\nMoyennes\n", fontContent, 1, 1.0f, "center", true));
        table.addCell(getPdfPCellCustomized("Apréciations générales", fontContent, 5, 10.0f, "center", true));

        for (Evaluation evaluation : evaluations) {
            //table moyennes
            table.addCell(getPdfPCellCustomized(evaluation.getModule().getEntitled() + "\n\nM. Louis", fontContent, 3, 5.0f, "left", true));
            table.addCell(getPdfPCellCustomized(Double.toString(evaluation.getAverage()), null, 1, 15.0f, "center", true));
            table.addCell(getPdfPCellCustomized("\n" + (evaluation.getComment() != null ? evaluation.getComment() : "")  + "\n\n", fontContent, 7, 5.0f, "left", true));

        }

        table.addCell(getPdfPCellCustomized("\nMoyenne générale\n\n\n", fontContent, 3, 5.0f, "", true));
        table.addCell(getPdfPCellCustomized("\n" + this.getAverageFromEvaluation(user.getId()) + "\n", null, 1, 5.0f, "center", true));
        table.addCell(getPdfPCellCustomized(null, null, 7, 0, "", true));

        document.add(table);

        //Bloc 3 : Vie scolaire ------------------------------------------------------------
        table = initPdfPTable(9, 100);

//        table.addCell(getPdfPCellCustomized("Vie scolaire\n\nMme Christine LUKOMBO", fontContent, 3, 5.0f, "left", true));
        table.addCell(getPdfPCellCustomized("Abscence(s) :     " + schoolLifeService.getAbsences(user.getId()).size() + " Excusée(s).\n                             2 Non excusée(s).\n\nRetard(s) :             " + schoolLifeService.getDelayStudents(user.getId()).size(), fontContent, 6, 3.0f, "left", true));

        document.add(table);

        //Bloc 4 : Result ------------------------------------------------------------------
        table = initPdfPTable(4, 100);

        //Entitled
        table.addCell(getPdfPCellCustomized("Observation du conseil de classe", fontContent, 2, 5.0f, "left", true));
        table.addCell(getPdfPCellCustomized("Avis", fontContent, 1, 5.0f, "left", true));
        table.addCell(getPdfPCellCustomized("Visa du chef d'Établissement", fontContent, 1, 5.0f, "left", true));


        //Content
        table.addCell(getPdfPCellCustomized(schoolReport.getComment(), fontContent, 2, 5.0f, "left", true));

        //NOTE : Félicitation / Encouragements/ Avertissement de travail
        table.addCell(getPdfPCellCustomized("\n"  + schoolReport.getGradeAword() +"\n\n", fontContent, 1, 5.0f, "left", true));

        table.addCell(getPdfPCellCustomized("\n" + firstNameManager + " " + lastNameManager + "\n\n", fontContent, 1, 5.0f, "left,", true));
        document.add(table);

        //Bloc 5 : Comment --------------------------------------------------------------
        table = initPdfPTable(2, 100);

        table.addCell(getPdfPCellCustomized("Bulletin à conserver précieusement.", fontComment, 1, 5.0f, "left", false));
        table.addCell(getPdfPCellCustomized(schoolReport.getCreationDate().toString(), fontComment, 1, 5.0f, "right", false));


        document.add(table);
    }

    /**
     * Create a PdfPCell object
     *
     * @param chain   String   Content to show.
     * @param colSpan int    Number of cells to merge.
     * @param padding float  Padding element content.
     * @param align   String   Align element content.
     * @param border  Boolean To show border cell
     * @return PdfPCell
     */
    private PdfPCell getPdfPCellCustomized(String chain, Font font, int colSpan, float padding, String align, boolean border) {
        PdfPCell cell;

        if (font == null)
            cell = new PdfPCell(new Paragraph(chain));
        else
            cell = new PdfPCell(new Paragraph(chain, font));

        if (colSpan >= 0)
            cell.setColspan(colSpan);

        if (padding >= 0)
            cell.setPadding(padding);

        switch (align) {
            case "left":
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
            case "right":
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                break;
            case "center":
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
            default:
                break;
        }

        if (!border) {
            cell.setBorder(PdfPCell.NO_BORDER);
        }

        return cell;
    }

    /**
     * Initialize a PdfPTable object
     *
     * @param colums          int The number of colums of the table
     * @param widthPercentage int The width of the table in percentage
     * @return PdfPTable
     */
    private PdfPTable initPdfPTable(int colums, int widthPercentage) {
        PdfPTable table = new PdfPTable(colums);
        table.setWidthPercentage(widthPercentage);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);

        return table;
    }

    private String generateQrCode() {
        String img = null;

        try {
            String data = "Je suis Thierry et je suis gentil.";
            int size = 50;

            // encode
            BitMatrix bitMatrix = generateMatrix(data, size);
            //create a temp file
            final File temp = File.createTempFile("temp-file-name", ".tmp");
            String imageFormat = "png";
            //Get tempropary file path
            String absolutePath = temp.getAbsolutePath();
            String tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

            img = tempFilePath + "/" + "qrcode-01." + imageFormat;

            writeImage(img, imageFormat, bitMatrix);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    private void writeImage(String outputFileName, String imageFormat, BitMatrix bitMatrix) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFileName));
            MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BitMatrix generateMatrix(String data, int size) {
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size);
            return bitMatrix;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }

    /**
     * Returns schools for a manager according to accountCode
     * @param accountCode
     * @return list of entities
     */
    @Override
    public List<SchoolDTO> getSchoolsByManager(Long accountCode) throws Exception {
        try {
            final LocalDate currentDate = LocalDate.now();

            final List<SchoolDTO> schoolDTOs = new ArrayList<>();

            final List<AssignmentManager> assignmentManagers = assignmentManagerRepository.findAllByCurrentSchoolYear(currentDate);

            final User user = userRepository.findOne(accountCode);
            final Manager manager = managerRepository.findByUser(user);

            for (final AssignmentManager assignmentManager : assignmentManagers) {
                if (assignmentManager.getManagers().contains(manager)) {
                    schoolDTOs.add(schoolMapper.toDto(assignmentManager.getSchool()));
                }
            }

            return schoolDTOs;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of schools " + e.getMessage());
            throw new Exception("Error during collecting of schools");
        }
    }

    /**
     * Returns classrooms for a manager according to accountCode
     * @param accountCode
     * @param idSchool
     * @return list of entities
     */
    @Override
    public List<ClassroomDTO> getClassroomsByManager(Long accountCode, Long idSchool) throws Exception {
        try {
            final LocalDate currentDate = LocalDate.now();

            final List<AssignmentManager> assignmentManagers = assignmentManagerRepository.findAllByCurrentSchoolYear(currentDate);

            final List<ClassroomDTO> classroomDTOs = new ArrayList<>();

            final User user = userRepository.findOne(accountCode);
            final Manager manager = managerRepository.findByUser(user);

            for (final AssignmentManager assignmentManager : assignmentManagers) {
                if (assignmentManager.getManagers().contains(manager) && assignmentManager.getSchool().getId().equals(idSchool)) {
                    classroomDTOs.addAll(this.getClassrooms(idSchool)
                        .stream()
                        .map(c -> classroomMapper.toDto(c))
                        .collect(Collectors.toList()));
                }
            }

            return classroomDTOs;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of classrooms " + e.getMessage());
            throw new Exception("Error during collecting of classrooms");
        }
    }

    private List<Classroom> getClassrooms(Long idSchool) {
        final LocalDate currentDate = LocalDate.now();
        final List<AssignmentModule> assignmentModules = assignmentModuleRepository.findAllByCurrentSchoolYear(currentDate);

        final List<Classroom> classrooms = new ArrayList<>();

        for (final AssignmentModule assignmentModule : assignmentModules) {
            if (assignmentModule.getSchool().getId().equals(idSchool)) {
                classrooms.add(assignmentModule.getClassroom());
            }
        }

        return classrooms;
    }

    /**
     * Returns students for a manager according to accountCode
     * @param accountCode
     * @param idSchool
     * @param idClassroom
     * @return list of entities
     */
    @Override
    public List<UserDTO> getStudentsByManager(Long accountCode, Long idSchool, Long idClassroom) throws Exception { // Supprimer le accountCode
        try {
            final List<UserDTO> userDTOs = new ArrayList<>();

            int i = 0;

            final LocalDate currentDate = LocalDate.now();

            final List<AssignmentModule> assignmentModules = assignmentModuleRepository.findAllByCurrentSchoolYear(currentDate);

            while (i < assignmentModules.size() && userDTOs.size() == 0) {
                if (assignmentModules.get(i).getSchool().getId().equals(idSchool) &&
                    assignmentModules.get(i).getClassroom().getId().equals(idClassroom)) {
                    userDTOs.addAll(this.getUsersByManager(idSchool, idClassroom));
                }
                i++;
            }

            return userDTOs;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of students " + e.getMessage());
            throw new Exception("Error during collecting of students");
        }
    }


    private List<UserDTO> getUsersByManager(final Long idSchool, final Long idClassroom) throws Exception {
        try {
            final LocalDate currentDate = LocalDate.now();

            final List<Inscription> inscriptions = inscriptionRepository.findAllByCurrentSchoolYear(currentDate);

            final List<UserDTO> userDTOs = new ArrayList<>();

            for (final Inscription inscription : inscriptions) {
                if (inscription.getSchool().getId().equals(idSchool) &&
                    inscription.getClassroom().getId().equals(idClassroom)) {
                    userDTOs.addAll(inscription.getStudents().stream().
                        map(u -> userMapper.userToUserDTO(u.getUser())).
                        collect(Collectors.toList()));
                }
            }

            return userDTOs;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of students " + e.getMessage());
            throw new Exception("Error during collecting of students");
        }
    }


    /**
     * Returns student for a manager according to accountCode
     * @param accountCode
     * @return entity
     */
    @Override
    public UserDTO getStudentByManager(Long accountCode) throws Exception {
        try {
            final User user = userRepository.findOne(accountCode);

            return userMapper.userToUserDTO(user);
        } catch (Exception e) {
            LOGGER.error("Error during collecting of user " + e.getMessage());
            throw new Exception("Error during collecting of user");
        }
    }

    /**
     * Save a schoolReport
     * @param schoolReportDTO
     * @return entity
     */
    @Override
    public SchoolReportDTO saveSchoolReport(SchoolReportDTO schoolReportDTO) throws Exception {
        try {
            schoolReportDTO.setCreationDate(LocalDate.now());

            final Student student = studentRepository.findOne(schoolReportDTO.getStudentId());
            final  School school = schoolMapper.toEntity(getSchoolForStudent(student));
            final Classroom classroom  = classroomMapper.toEntity(getClassroomForStudent(student));
            final YearPeriod yearPeriod = findYearPeriodForSchoolReport(LocalDate.now(), school, classroom);
            schoolReportDTO.setYearPeriodId(yearPeriod.getId());
            SchoolReport schoolReport = schoolReportMapper.toEntity(schoolReportDTO);
            schoolReport.setEvaluations(this.getEvaluationsByStudent(student.getUser().getId())
                .stream()
                .map( e -> evaluationMapper.toEntity(e))
                .collect(Collectors.toSet()));

            return this.schoolReportService.save(schoolReportMapper.toDto(schoolReport));
        } catch (Exception e) {
            LOGGER.error("Error during saving of user " + e.getMessage());
            throw new Exception("Error during saving of user");
        }
    }

    private YearPeriod findYearPeriodForSchoolReport(LocalDate periodDate, School school, Classroom classroom) {
        final List<AssignmentYearPeriod>  assignmentYearPeriods = this.assignmentYearPeriodRepository.findAllWithEagerRelationships();

        YearPeriod yearPeriod = null;

        for (AssignmentYearPeriod assignmentYearPeriod : assignmentYearPeriods) {
            if (assignmentYearPeriod.getSchool().equals(school)) {
                if (assignmentYearPeriod.getClassrooms().contains(classroom)) {

                    for (YearPeriod y : assignmentYearPeriod.getYearPeriods()) {
                        if (y.getEndDate().isBefore(periodDate) || y.getEndDate().equals(periodDate) ) {
                            yearPeriod = y;
                        }
                    }

                }
            }
        }

        return yearPeriod;
    }

    private SchoolDTO getSchoolForStudent(Student student) {
        List <Inscription> inscriptions = inscriptionRepository.findAllByCurrentSchoolYear(LocalDate.now());

        School school = null;

        for (Inscription inscription : inscriptions) {
            for (Student s : inscription.getStudents()) {
                if (s.equals(student)) {
                    school = inscription.getSchool();
                }
            }
        }

        return this.schoolMapper.toDto(school);
    }

    private ClassroomDTO getClassroomForStudent(Student student) {
        List <Inscription> inscriptions = inscriptionRepository.findAllByCurrentSchoolYear(LocalDate.now());

        Classroom classroom = null;

        for (Inscription inscription : inscriptions) {
            for (Student s : inscription.getStudents()) {
                if (s.equals(student)) {
                    classroom = inscription.getClassroom();
                }
            }
        }

        return this.classroomMapper.toDto(classroom);
    }

    /**
     * Returns evaluations of a Student
     * @param accountCode
     * @return list of entities
     */
    public Set<EvaluationDTO> getEvaluationsByStudent(final Long accountCode) {
        final User user = userRepository.findOne(accountCode);

        final Student student = studentRepository.findStudentByUser(user);

        final  School school = schoolMapper.toEntity(getSchoolForStudent(student));
        final Classroom classroom  = classroomMapper.toEntity(getClassroomForStudent(student));
        final YearPeriod yearPeriod = findYearPeriodForSchoolReport(LocalDate.now(), school, classroom);

        final ZonedDateTime pastDateTime = ZonedDateTime.now(TimeZone.getTimeZone("Europe/Madrid").toZoneId()).minusYears(1);

        final List<Evaluation> evaluations = evaluationRepository.getEvaluationByStudentAndPeriod(student.getId(), pastDateTime);

        final Set<EvaluationDTO> evaluationList = new HashSet<>();

        final ZonedDateTime dateTimeEnd = yearPeriod.getEndDate().atStartOfDay(ZoneOffset.UTC);

        for (Evaluation evaluation : evaluations) {
            if (evaluation.getEvaluationDate().isBefore(dateTimeEnd) || evaluation.getEvaluationDate().isEqual(dateTimeEnd)) {
                evaluationList.add(evaluationMapper.toDto(evaluation));
            }
        }

        return evaluationList;
    }

    private String getAverageFromEvaluation(final Long accountCode) {
        final Set<Evaluation> evaluations = this.getEvaluationsByStudent(accountCode).stream().map(e -> evaluationMapper.toEntity(e)).collect(Collectors.toSet());

        double sum = 0;
        int nbTotal = 0;

        final NumberFormat instance = DecimalFormat.getNumberInstance(new Locale("##.##"));

        for (final Evaluation evaluation : evaluations) {
            sum += evaluation.getAverage();
            nbTotal++;
        }

        return (nbTotal > 0) ? instance.format(sum / nbTotal) : "";
    }

    /**
     * Returns manager according of User
     * @param userDTO
     * @return ManagerDTO
     */
    public ManagerDTO findByUser(UserDTO userDTO) throws Exception {
        try {
            return managerMapper.toDto(this.managerRepository.findByUser(userMapper.userDTOToUser(userDTO)));
        } catch (Exception e) {
            LOGGER.error("Error during collecting of manager " + e.getMessage());
            throw new Exception("Error during collecting of manager");
        }
    }

}
