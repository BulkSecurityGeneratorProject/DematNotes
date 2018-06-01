package org.csid.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.csid.domain.InscriptionModule;
import org.csid.domain.Intervention;
import org.csid.domain.Module;
import org.csid.domain.School;
import org.csid.domain.Student;
import org.csid.domain.Teacher;
import org.csid.domain.User;
import org.csid.repository.InscriptionModuleRepository;
import org.csid.repository.InterventionRepository;
import org.csid.repository.ModuleRepository;
import org.csid.repository.SchoolRepository;
import org.csid.repository.StudentRepository;
import org.csid.repository.TeacherRepository;
import org.csid.repository.UserRepository;
import org.csid.service.IStudentService;
import org.csid.service.dto.ClassroomDTO;
import org.csid.service.dto.UserDTO;
import org.csid.service.mapper.ClassroomMapper;
import org.csid.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("IStudentService")
public class StudentServiceImpl_ implements IStudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private InterventionRepository interventionRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private InscriptionModuleRepository inscriptionModuleRepository;

	@Autowired
	private ModuleRepository moduleRepository;

	@Autowired
	private ClassroomMapper classroomMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserDTO> findStudentByClassroom(final Long idSchool, final Long idClassroom, final Long idSchoolYear) {
		final List<Student> students = studentRepository.findAll();

//		return students
//				.stream()
//				.filter(s -> s.getSchool().getWording().trim().equals(school.getWording().trim()) && s.getClassroom().getEntitled().trim().equals(classroom.getEntitled().trim()))
//				.map(s -> studentMapper.toDto(s)).collect(Collectors.toList());
		final List<UserDTO> userDTOs = new ArrayList<>(); 
		for (Student student : students) {
//			if (student.getSchool().getWording().trim().equals(school.getWording().trim()) && student.getClassroom().getEntitled().trim().equals(classroom.getEntitled().trim()) && student.getSchoolYear().equals(schoolYear)) {
				User u = userRepository.findOne(student.getUser().getId());
				userDTOs.add(userMapper.userToUserDTO(u));
//			}
		}
		return userDTOs;
	}

	@Override
	public List<ClassroomDTO> findClassroomForTeacher(final Long idTeacher, final Long idSchool) {
		final Teacher t = teacherRepository.findOne(idTeacher);
		
		final List<Intervention> interventions = interventionRepository.findAllByTeacher(t);

		final ZonedDateTime z = ZonedDateTime.now(ZoneId.systemDefault());

		final School s = schoolRepository.findOne(idSchool);

		final List<ClassroomDTO> classroomDTOs = new ArrayList<>();

		for (final Intervention intervention : interventions) {
			if (intervention.getModule().getSchool().equals(s) && intervention.getStartDate().isBefore(z)) {
				classroomDTOs.addAll(this.findClassroomsByModule(intervention.getModule().getId()));
				break;
			}
		}

		return classroomDTOs;
	}

	private List<ClassroomDTO> findClassroomsByModule(final Long idModule) {
		final Module m = moduleRepository.findOne(idModule);

		final List<InscriptionModule> inscriptionModules = inscriptionModuleRepository.findAllByModule(m);

		final List<ClassroomDTO> classroomDTOs = new ArrayList<>();

		final LocalDate currentDate = LocalDate.now();


		for (final InscriptionModule inscriptionModule : inscriptionModules) {
			if (inscriptionModule.getStudent().getSchoolYear().getStartDate().isBefore(currentDate) &&
					inscriptionModule.getStudent().getSchoolYear().getEndDate().isAfter(currentDate)) {
				classroomDTOs.add(classroomMapper.toDto(inscriptionModule.getStudent().getClassroom()));
			}
		}

		return classroomDTOs;
	}
}
