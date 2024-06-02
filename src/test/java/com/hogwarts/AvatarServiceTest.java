package com.hogwarts;

import com.hogwarts.imagess3.ImagesS3Application;
import com.hogwarts.imagess3.exceptions.NoSuchSubject;
import com.hogwarts.imagess3.repositories.s3.AvatarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ImagesS3Application.class)
public class AvatarServiceTest {
    @Autowired
    private AvatarRepository avatarRepository;

    private final static Long notPresentId = -1L;
    private final static Long presentId = -2L;

    @BeforeEach
    public void initializeFiles() {
        avatarRepository.updateSubject(presentId, new byte[]{1, 2, 3, 4, 5});
    }

    @AfterEach
    public void deleteFiles() {
        try {
            avatarRepository.deleteSubject(presentId);
        } catch (NoSuchSubject ignored) {
        }
    }

    @Test
    public void testGetImages() {
        assertThrowsExactly(NoSuchSubject.class,
                () -> avatarRepository.getSubject(notPresentId));

        assertDoesNotThrow(() -> avatarRepository.getSubject(presentId));
        assertNotNull(avatarRepository.getSubject(presentId));
    }

    @Test
    public void testPresentImages() {
        assertEquals(false, avatarRepository.subjectIsPresent(notPresentId));
        assertEquals(true, avatarRepository.subjectIsPresent(presentId));
    }

    @Test
    public void testPresentAfterUpdate() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> avatarRepository.updateSubject(presentId, new byte[]{1}));
        executor.execute(() -> assertEquals(true, avatarRepository.subjectIsPresent(presentId)));

        executor.execute(() -> avatarRepository.updateSubject(presentId, new byte[]{1}));
        executor.execute(() -> assertEquals(true, avatarRepository.subjectIsPresent(presentId)));
    }
}
