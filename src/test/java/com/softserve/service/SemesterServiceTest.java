package com.softserve.service;

import com.softserve.dto.SemesterDTO;
import com.softserve.dto.SemesterWithGroupsDTO;
import com.softserve.entity.Group;
import com.softserve.entity.Period;
import com.softserve.entity.Semester;
import com.softserve.exception.*;
import com.softserve.mapper.SemesterMapper;
import com.softserve.repository.GroupRepository;
import com.softserve.repository.ScheduleRepository;
import com.softserve.repository.SemesterRepository;
import com.softserve.service.impl.SemesterServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class SemesterServiceTest {

    @Mock
    private SemesterRepository semesterRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private SemesterMapper semesterMapper;

    @InjectMocks
    private SemesterServiceImpl semesterService;

    @Nested
    @DisplayName("Варіант 7: Специфічні тести логіки семестрів")
    class Variant7Tests {

        @Test
        @DisplayName("copySemester: Успішне копіювання груп, днів, періодів та розкладу")
        void copySemester_HappyPath() {
            // Arrange
            Long fromId = 1L;
            Long toId = 2L;
            Semester sourceSemester = createSemester(fromId, "Old", 2020);
            when(semesterRepository.findById(fromId)).thenReturn(Optional.of(sourceSemester));

            // Act
            semesterService.copySemester(fromId, toId);

            // Assert
            verify(semesterRepository).copyGroups(fromId, toId);
            verify(semesterRepository).copyDays(fromId, toId);
            verify(semesterRepository).copyPeriods(fromId, toId);
            verify(scheduleRepository).copySchedule(fromId, toId);
        }

        @Test
        @DisplayName("getById: Перевірка фільтрації відключених (disabled) груп")
        void getById_ShouldFilterDisabledGroups() {
            // Arrange
            Long id = 1L;
            Group activeGroup = createGroup(1L, "Active");
            Group disabledGroup = createGroup(2L, "Disabled");
            disabledGroup.setDisable(true);

            Semester semester = createSemester(id, "Test", 2020);
            semester.setGroups(new HashSet<>(Set.of(activeGroup, disabledGroup)));

            SemesterWithGroupsDTO expectedDto = createSemesterWithGroupsDTO(id, "Test", 2020);
            
            when(semesterRepository.findById(id)).thenReturn(Optional.of(semester));
            when(semesterMapper.semesterToSemesterWithGroupsDTO(any(Semester.class))).thenReturn(expectedDto);

            // Act
            semesterService.getById(id);

            // Assert
            // Перевіряємо, що в мапер потрапив семестр лише з активною групою
            verify(semesterMapper).semesterToSemesterWithGroupsDTO(argThat(s -> 
                s.getGroups().size() == 1 && s.getGroups().contains(activeGroup)
            ));
        }

        @Test
        @DisplayName("addGroupsToSemester: Порожній список groupIds не повинен викликати оновлення")
        void addGroupsToSemester_WithEmptyList_ShouldNotTriggerUpdate() {
            // Act
            semesterService.addGroupsToSemester(1L, Collections.emptyList());

            // Assert
            verify(semesterRepository, never()).update(any());
            verify(groupRepository, never()).getGroupsByGroupIds(any());
        }
    }

    @Nested
    @DisplayName("Базові CRUD операції")
    class BasicCrudTests {

        @Test
        void getSemesterById_Success() {
            Semester semester = createSemester(1L, "1 semester", 2020);
            SemesterWithGroupsDTO expectedDTO = createSemesterWithGroupsDTO(1L, "1 semester", 2020);

            when(semesterRepository.findById(1L)).thenReturn(Optional.of(semester));
            when(semesterMapper.semesterToSemesterWithGroupsDTO(semester)).thenReturn(expectedDTO);

            SemesterWithGroupsDTO result = semesterService.getById(1L);

            assertNotNull(result);
            verify(semesterRepository).findById(1L);
        }

        @Test
        void saveSemester_Success() {
            SemesterWithGroupsDTO inputDTO = createSemesterWithGroupsDTO(null, "New", 2020);
            Semester semester = createSemester(null, "New", 2020);
            
            when(semesterMapper.semesterWithGroupsDTOToSemester(inputDTO)).thenReturn(semester);
            when(semesterRepository.save(semester)).thenReturn(semester);

            semesterService.save(inputDTO);

            verify(semesterRepository).save(semester);
        }

        @Test
        void deleteSemester_Success() {
            Semester semester = createSemester(1L, "Delete me", 2020);
            when(semesterRepository.findById(1L)).thenReturn(Optional.of(semester));

            semesterService.delete(1L);

            verify(semesterRepository).delete(semester);
        }
    }

    @Nested
    @DisplayName("Тести валідації та виключень")
    class ValidationTests {

        @Test
        void throwIncorrectTimeExceptionIfStartAfterEnd() {
            SemesterWithGroupsDTO inputDTO = createSemesterWithGroupsDTO(null, "Error", 2020);
            inputDTO.setStartDay(LocalDate.of(2020, 10, 1));
            inputDTO.setEndDay(LocalDate.of(2020, 9, 1));
            
            Semester semester = new Semester();
            semester.setStartDay(inputDTO.getStartDay());
            semester.setEndDay(inputDTO.getEndDay());

            when(semesterMapper.semesterWithGroupsDTOToSemester(inputDTO)).thenReturn(semester);

            assertThrows(IncorrectTimeException.class, () -> semesterService.save(inputDTO));
        }

        @Test
        void throwEntityAlreadyExistsException() {
            SemesterWithGroupsDTO inputDTO = createSemesterWithGroupsDTO(null, "Exists", 2020);
            when(semesterMapper.semesterWithGroupsDTOToSemester(inputDTO)).thenReturn(new Semester());
            when(semesterRepository.getSemesterByDescriptionAndYear(any(), anyInt()))
                    .thenReturn(Optional.of(new Semester()));

            assertThrows(EntityAlreadyExistsException.class, () -> semesterService.save(inputDTO));
        }
    }

    // ==================== Допоміжні методи ====================

    private Semester createSemester(Long id, String description, int year) {
        Semester semester = new Semester();
        semester.setId(id);
        semester.setYear(year);
        semester.setDescription(description);
        semester.setStartDay(LocalDate.of(2020, 4, 10));
        semester.setEndDay(LocalDate.of(2020, 5, 10));
        semester.setGroups(new HashSet<>());
        return semester;
    }

    private SemesterWithGroupsDTO createSemesterWithGroupsDTO(Long id, String description, int year) {
        SemesterWithGroupsDTO dto = new SemesterWithGroupsDTO();
        dto.setId(id);
        dto.setYear(year);
        dto.setDescription(description);
        dto.setStartDay(LocalDate.of(2020, 4, 10));
        dto.setEndDay(LocalDate.of(2020, 5, 10));
        return dto;
    }

    private Group createGroup(Long id, String title) {
        Group group = new Group();
        group.setId(id);
        group.setTitle(title);
        group.setDisable(false);
        return group;
    }
}
