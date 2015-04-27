package com.example.databaseexample;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.databaseexample.utils.ValidationUtils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by sohammondal on 25/04/15.
 */
@SmallTest
public class EmailValidationTests {

    @Before
    public void setup(){

    }

    @After
    public void tearDown(){

    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(ValidationUtils.isValidEmail("name@email.com")).isTrue();
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertThat(ValidationUtils.isValidEmail("name@email.co.uk")).isTrue();
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertThat(ValidationUtils.isValidEmail("name@email")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertThat(ValidationUtils.isValidEmail("name@email..com")).isFalse();
    }

    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertThat(ValidationUtils.isValidEmail("@email.com")).isFalse();
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertThat(ValidationUtils.isValidEmail("")).isFalse();
    }

    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertThat(ValidationUtils.isValidEmail(null)).isFalse();
    }
}
