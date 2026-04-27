package com.softserve.service;

import com.softserve.dto.TeacherDTO;
import com.softserve.dto.TeacherForUpdateDTO;
import com.softserve.dto.TeacherImportDTO;
import com.softserve.dto.UserDataDTO;
import com.softserve.entity.enums.Role;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface TeacherService {
    TeacherDTO getById(Long id);
    List<TeacherDTO> getAll();
    TeacherDTO save(TeacherDTO teacherDTO);
    TeacherForUpdateDTO update(TeacherForUpdateDTO teacherForUpdateDTO);
    void deleteById(Long id);
    List<TeacherDTO> getDisabled();
    List<TeacherDTO> getAllTeacherWithoutUser();
    List<TeacherImportDTO> saveFromFile(MultipartFile file, Long departmentId);
    TeacherImportDTO saveTeacher(Long departmentId, TeacherImportDTO teacher);
    void removeUserFromTeacher(Long userId);
    UserDataDTO getUserDataByUserId(Long userId);
}