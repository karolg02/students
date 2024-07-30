package com.karol.students.controller;

import ch.qos.logback.core.util.StringUtil;
import com.karol.students.repository.StudentRepository;
import com.karol.students.model.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody @Valid Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Optional<Student> studentOptional = studentRepository.findById(id);
        return studentOptional.map(ResponseEntity::ok).orElseGet(() ->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        return studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            return ResponseEntity.ok().build();
        }).orElseGet(() ->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id, @RequestBody Student student){
        return studentRepository.findById(id).map(studentFromDb -> {
            studentFromDb.setFirstName(student.getFirstName());
            studentFromDb.setLatName(student.getLatName());
            studentFromDb.setEmail(student.getEmail());
            return ResponseEntity.ok().body(studentRepository.save(studentFromDb));
        }).orElseGet(() ->ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, @RequestBody Student student){
        return studentRepository.findById(id).map(studentFromDb -> {
            if(!StringUtils.isEmpty(student.getFirstName())){
            studentFromDb.setFirstName(student.getFirstName());
            }
            if(!StringUtils.isEmpty(student.getLatName())) {
                studentFromDb.setLatName(student.getLatName());
            }
            return ResponseEntity.ok().body(studentRepository.save(studentFromDb));
        }).orElseGet(() ->ResponseEntity.notFound().build());
    }

}
