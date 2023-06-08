package com.techelevator.controller;

import com.techelevator.dao.CourseDao;
import com.techelevator.dao.LessonDao;
import com.techelevator.dao.SubmissionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Submission;
import com.techelevator.model.SubmissionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class SubmissionController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SubmissionDao submissionDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private LessonDao lessonDao;

    @PostMapping("/submission")
    @PreAuthorize("hasRole('USER')")
    public SubmissionDTO postSubmission(@RequestBody SubmissionDTO submissionDto, Principal principal) {
        // On the frontend the lessonID should not have to be entered by the user
        // This lesson id can be received from the path lesson/:lessonID I believe

        int studentId = userDao.findIdByUsername(principal.getName());
        Submission submission = submissionDao.mapSubmissionDtoToSubmission(submissionDto, submissionDto.getLessonId()
                , studentId);

        Submission createdSubmission = submissionDao.postSubmission(submission);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        String submissionDateTime = LocalDateTime.now().format(formatter);
        createdSubmission.setSubmittedAt(submissionDateTime);

        String loggedInUserFullName = userDao.getNameByUsername(principal.getName());
        String courseName = courseDao.getCourseNameFromLessonId(createdSubmission.getLessonId());
        String lessonName = lessonDao.getLessonNameFromLessonId(createdSubmission.getLessonId());

        SubmissionDTO createdSubmissionDto = submissionDao.mapSubmissionToSubmissionDto(createdSubmission,
                loggedInUserFullName, courseName, lessonName);

        return createdSubmissionDto;
    }

    @GetMapping("/submission/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Submission getSubmission(@PathVariable int id) {
        return submissionDao.getSubmission(id);
    }

    @DeleteMapping("/submission/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubmission(@PathVariable int id) {
        submissionDao.deleteSubmission(id);
    }

    // teacherSetSubmissonGrade teacher only
    @PutMapping("/submission/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Submission setSubmissionGrade(@RequestBody Submission submission, @PathVariable int id) {
        Submission submissionToUpdate = getSubmission(id);
        submissionToUpdate.setGrade(submission.getGrade());

        submissionDao.setSubmissionGrade(submission, id);

        return submissionToUpdate;
    }

    // teacher can see a list of submissions for a lesson
    // another getSubmissionsForLesson method
    @GetMapping("/lesson/{id}/submissions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Submission> getSubmissionsForLesson(@PathVariable int id) {
        return submissionDao.getSubmissionsForLesson(id);
    }

    @GetMapping("/module/{id}/submissions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Submission> getSubmissionsForModule(@PathVariable int id) {
        return submissionDao.getSubmissionsForModule(id);
    }

    @GetMapping("/course/{id}/submissions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Submission> getSubmissionsForCourse(@PathVariable int id) {
        return submissionDao.getSubmissionsForCourse(id);
    }

    @GetMapping("/course/{courseId}/student/{studentId}/submissions")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Submission> getSubmissionsForCourseAndStudent(@PathVariable int courseId, @PathVariable int studentId) {
        System.out.println("Got Request");
        return submissionDao.getSubmissionsForCourseAndStudent(courseId, studentId);
    }

    @GetMapping("/lesson/{lessonId}/student/{studentId}")
    public Submission getSubmissionByLessonAndStudentId(@PathVariable int lessonId, @PathVariable int studentId) {
        return submissionDao.getSubmissionByLessonAndStudentId(lessonId, studentId);
    }

    @GetMapping("/course/{courseId}/student/{studentId}/grade")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public BigDecimal getStudentGradeAverageForCourse(@PathVariable int courseId, @PathVariable int studentId) {
        return submissionDao.getGradeAverageForCourse(studentId, courseId);
    }
}
