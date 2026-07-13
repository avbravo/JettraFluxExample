package com.flux.example.pages;

import io.jettra.test.annotation.JettraTest;
import io.jettra.test.core.JettraAssert;

public class FormsPageTest {
    @JettraTest
    public void testTitle() {
        FormsPage page = new FormsPage();
        JettraAssert.assertEquals("Forms - JettraFlux Pro", page.getTitle(), "Title should match");
    }
}
