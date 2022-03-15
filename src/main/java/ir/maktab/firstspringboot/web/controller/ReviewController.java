package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.review.ReviewCreateParam;
import ir.maktab.firstspringboot.api.review.ReviewCreateResult;
import ir.maktab.firstspringboot.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAuthority('review:write')")
    public ResponseEntity<ReviewCreateResult> addReview(@RequestBody ReviewCreateParam createParam) {
        ReviewCreateResult result = reviewService.save(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
