package org.csid.service.impl;

import org.csid.domain.*;
import org.csid.repository.*;
import org.csid.service.ISchoolLifeService;
import org.csid.service.dto.*;
import org.csid.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("ISchoolLifeService")
@Transactional
public class SchoolLifeServiceImpl_ implements ISchoolLifeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolLifeServiceImpl_.class);

    @Value("${path.upload}")
    private String path;

    private static final ZonedDateTime pastDate = ZonedDateTime.now().minusYears(1);

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private AbsenceMapper absenceMapper;

    @Autowired
    private DelayStudentRepository delayStudentRepository;

    @Autowired
    private DelayStudentMapper delayStudentMapper;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private AssignmentModuleRepository assignmentModuleRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleMapper moduleMapper;


    /**
     * Returns the absences of a student according to accountCode User
     * @param accountCode
     * @return the list of entities
     */
    public List<AbsenceDTO> getAbsences(final Long accountCode) throws Exception {
        final List<AbsenceDTO> absenceDTOs = new ArrayList<>();

        final User user;
        final Student student;
        final List<Absence> absences;

        try {
            user = userRepository.findOne(accountCode);
            student = studentRepository.findStudentByUser(user);
            absences = absenceRepository.findAllAbsencesByPeriod(this.pastDate);

            for (final Absence absence : absences) {
                for (final Student s : absence.getStudents()) {
                    if (s.equals(student) && !absenceDTOs.contains(absenceMapper.toDto(absence))) {
                        absenceDTOs.add(absenceMapper.toDto(absence));
                    }
                }
            }

            return absenceDTOs;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of absences " + e.getMessage());
            throw new Exception("Error during collecting of absences");
        }
    }

    /**
     * Returns the delays of a student according to accountCode User
     * @param accountCode
     * @return the list of entities
     */
    @Override
    public List<DelayStudentDTO> getDelayStudents(final Long accountCode) throws Exception {
        final List<DelayStudentDTO> delayStudentDTOs = new ArrayList<>();

        final User user;
        final Student student;
        final List<DelayStudent> delayStudents;

        try {
            user = userRepository.findOne(accountCode);
            student = studentRepository.findStudentByUser(user);
            delayStudents = delayStudentRepository.findAllDelayStudentsByPeriod(this.pastDate);

            for (final DelayStudent delayStudent : delayStudents) {
                for (final Student s : delayStudent.getStudents()) {
                    if (s.equals(student) && !delayStudentDTOs.contains(delayStudentMapper.toDto(delayStudent))) {
                        delayStudentDTOs.add(delayStudentMapper.toDto(delayStudent));
                    }
                }
            }

            return delayStudentDTOs;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of delays " + e.getMessage());
            throw new Exception("Error during collecting of delays");
        }
    }

    /**
     * Returns the modules of a Teacher link to the classe
     * @param accountCode
     * @param idClassroom
     * @return the list of entities
     */
    public List<ModuleDTO> getModules(final Long accountCode, final Long idClassroom) throws Exception {
        final LocalDate currentDate = LocalDate.now();

        final List<AssignmentModule> assignmentModules;

        final User user;
        final Teacher teacher;
        final Classroom classroom;

        final List<ModuleDTO> moduleDTOS = new ArrayList<>();

        try {

            user = userRepository.findOne(accountCode);
            teacher = teacherRepository.findByUser(user);
            classroom = classroomRepository.findOne(idClassroom);

            assignmentModules = assignmentModuleRepository.findAllByCurrentSchoolYear(currentDate);

            for (AssignmentModule assignmentModule: assignmentModules) {
                if (assignmentModule.getClassroom().equals(classroom)) {
                    for (Teacher t : assignmentModule.getTeachers()) {
                        if (t.equals(teacher)) {
                            moduleDTOS.addAll(assignmentModule.getModules()
                                .stream()
                                .map(module -> moduleMapper.toDto(module))
                                .collect(Collectors.toList()));
                        }
                    }
                }
            }

            return moduleDTOS;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of modules " + e.getMessage());
            throw new Exception("Error during collecting of modules");
        }
    }

    /**
     * Saves Absences of students in a module
     * @return entity
     */
    public AbsenceDTO saveAbsencesModules(AbsenceSearchDTO absenceSearchDTO) throws Exception {
        try {
            Absence absence = new Absence();
            absence.setStudents(this.findAllStudentByAccountsCode(absenceSearchDTO.getAccountsCode()));
            absence.setStartDate(absenceSearchDTO.getStartDate());
            absence.setEndDate(absenceSearchDTO.getEndDate());
            absence.setModule(moduleRepository.findOne(absenceSearchDTO.getModuleId()));

            return absenceMapper.toDto(absenceRepository.save(absence));
        } catch (Exception e) {
            LOGGER.error("Error during saving of absences " + e.getMessage());
            throw new Exception("Error during saving of absences");
        }
    }

    private Set<Student> findAllStudentByAccountsCode(Set<Long> accountsCode) throws Exception {
        Set<Student> students;

        try {
            List<Student> studentList = studentRepository.findAll();

            students = new HashSet<>();

            for (Student student: studentList) {
                if (accountsCode.contains(student.getUser().getId())) {
                    students.add(student);
                }
            }

            return students;
        } catch (Exception e) {
            LOGGER.error("Error during collecting of students " + e.getMessage());
            throw new Exception("Error during collecting of students");
        }
    }

    public void store(MultipartFile file, Long idStudent) {
        final Path rootLocation = Paths.get(path + "/schoolLife/" + idStudent);

        if (!new File(rootLocation + "").exists()) {
            new File(rootLocation + "").mkdirs();
        }

        final File fileTmp = new File(rootLocation + "/" + file.getOriginalFilename());

        if(fileTmp.exists()) {
            fileTmp.delete();
        }

        try {
            Files.copy(file.getInputStream(), rootLocation.resolve(file.getOriginalFilename()));

            final Student student = studentRepository.findOne(idStudent);

            final Document document = new Document();
            document.setStudent(student);
            document.setEntitled(file.getOriginalFilename());
            document.setUrl(idStudent + "/" + file.getOriginalFilename());
            document.setVisible(true);
            document.setType(file.getContentType());

            documentRepository.save(document);

        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public List<DocumentDTO> getAllFiles(final Long idStudent) {
        final Student student = studentRepository.findOne(idStudent);

        final List<Document> documents = documentRepository.findAllByStudent(student);

        final List<DocumentDTO> documentDTOs = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            documentDTOs.add(i, documentMapper.toDto(documents.get(i)));
            final String urlTemp = path + "/schoolLife/" + documentMapper.toDto(documents.get(i)).getUrl();
            documentDTOs.get(i).setUrl(urlTemp);
        }

        return documentDTOs;
    }

    public Map<String,File> getFile(final Long idDocument) {
        final Document document = documentRepository.findOne(idDocument);

        final DocumentDTO documentDTO = documentMapper.toDto(document);
        documentDTO.setUrl(path + "/schoolLife/" + documentDTO.getUrl());

        Map<String,File> content = new HashMap<>();
        content.put(documentDTO.getType(), new File(documentDTO.getUrl()));

        return content;
    }

    public Boolean deleteFile(final Long idDocument) {
        final Document document = documentRepository.findOne(idDocument);

        Boolean isDeleted = false;

        if (document != null) {
            try {
                final File file = new File(path + "/schoolLife/" + document.getUrl());

                if (file.exists()) {
                    file.delete();
                    isDeleted = true;
                }

            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                documentRepository.delete(idDocument);
            }
        }

        return isDeleted;
    }

}
