package com.softserve.service;

import com.softserve.dto.TeacherDTO;
import com.softserve.dto.TeacherForUpdateDTO;
import com.softserve.entity.Teacher;
import com.softserve.entity.User;
import com.softserve.entity.enums.Role;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.FieldAlreadyExistsException;
import com.softserve.mapper.TeacherMapper;
import com.softserve.repository.DepartmentRepository;
import com.softserve.repository.TeacherRepository;
import com.softserve.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.softserve.entity.enums.Role.ROLE_TEACHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private UserService userService;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacherWithId1LAndWithUserId1;
    private Teacher teacherWithId1LAndWithoutUser;
    private Teacher teacherWithoutId;
    private TeacherDTO teacherDtoWithoutId;
    private TeacherDTO teacherDtoWithId1L;

    @BeforeEach
    void setUp() {
        String name = "Name1";
        String surname = "Surname1";
        String email = "teacher@gmail.com";

        teacherWithoutId = new Teacher();
        teacherWithoutId.setName(name);
        teacherWithoutId.setSurname(surname);

        teacherWithId1LAndWithUserId1 = new Teacher();
        teacherWithId1LAndWithUserId1.setId(1L);
        teacherWithId1LAndWithUserId1.setUserId(1L);

        teacherWithId1LAndWithoutUser = new Teacher();
        teacherWithId1LAndWithoutUser.setId(1L);

        teacherDtoWithoutId = new TeacherDTO();
        teacherDtoWithoutId.setName(name);
        teacherDtoWithoutId.setSurname(surname);
        teacherDtoWithoutId.setEmail(email);

        teacherDtoWithId1L = new TeacherDTO();
        teacherDtoWithId1L.setId(1L);
        teacherDtoWithId1L.setEmail(email);
    }

    /* --- ЗАКОМЕНТОВАНО ДЛЯ ЗВІТУ "ДО" ---
    @Nested
    @DisplayName("Варіант 7: Тести методу save (Негативні сценарії)")
    class SaveTeacherVariant7Tests {

        @Test
        @DisplayName("Should throw FieldAlreadyExistsException when email belongs to manager")
        void save_EmailBelongsToManager_ThrowsException() {
            TeacherDTO inputDTO = teacherDtoWithoutId;
            inputDTO.setEmail("manager@university.com");
            when(userService.existsByEmailAndRole(inputDTO.getEmail(), Role.ROLE_MANAGER)).thenReturn(true);
            assertThrows(FieldAlreadyExistsException.class, () -> teacherService.save(inputDTO));
        }

        @Test
        @DisplayName("Should throw FieldAlreadyExistsException when email is already used by another teacher")
        void save_EmailAlreadyUsedByTeacher_ThrowsException() {
            TeacherDTO inputDTO = teacherDtoWithoutId;
            when(teacherRepository.existsByEmail(inputDTO.getEmail())).thenReturn(true);
            assertThrows(FieldAlreadyExistsException.class, () -> teacherService.save(inputDTO));
        }
    }
    */

    @Nested
    @DisplayName("Базові тести отримання даних")
    class BasicReadTests {
        @Test
        void getAll() {
            List<Teacher> teachers = Collections.singletonList(teacherWithId1LAndWithUserId1);
            List<TeacherDTO> expectedDTOs = Collections.singletonList(teacherDtoWithId1L);
            when(teacherRepository.getAll()).thenReturn(teachers);
            when(teacherMapper.teachersToTeacherDTOs(teachers)).thenReturn(expectedDTOs);
            List<TeacherDTO> actualTeachers = teacherService.getAll();
            assertThat(actualTeachers).hasSameSizeAs(expectedDTOs);
        }

        @Test
        void getById() {
            when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacherWithId1LAndWithUserId1));
            when(teacherMapper.teacherToTeacherDTO(any())).thenReturn(teacherDtoWithId1L);
            TeacherDTO result = teacherService.getById(1L);
            assertNotNull(result);
        }
    }
}