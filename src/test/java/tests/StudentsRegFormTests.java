package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class StudentsRegFormTests extends TestBase {
    Faker faker = new Faker();

    String firstname = faker.name().firstName(),
            lastname = faker.name().lastName(),
            useremail = faker.internet().emailAddress(),
            userphone = faker.number().digits(10),
            usersubject = "History",
            useraddress = faker.address().streetAddress(),
            state = "Haryana",
            city = "Karnal";

    @Test
    void successfulFillTest() {
        step("Open reg form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });
        step("Fill reg form", () -> {
            $("#firstName").setValue(firstname);
            $("#lastName").setValue(lastname);
            $("#userEmail").setValue(useremail);
            $(byText("Female")).click();
            $("#userNumber").setValue(userphone);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue("2");
            $(".react-datepicker__year-select").selectOptionByValue("1964");
            $("[aria-label='Choose Wednesday, March 4th, 1964']").click();
            $("#subjectsInput").setValue(usersubject).pressEnter();
            $(byText("Reading")).click();
            $("#uploadPicture").uploadFromClasspath("img/ScarletWitch.jpg");
            $("#currentAddress").setValue(useraddress);
            $("#react-select-3-input").setValue(state).pressEnter();
            $("#react-select-4-input").setValue(city).pressEnter();
        });
        step("Submit from", () -> $("#submit").click());
        step("Verify successful submit", () -> {
            $(".modal-content").shouldBe(visible).shouldHave(
                    text(firstname),
                    text(lastname),
                    text(useremail),
                    text("Female"),
                    text(userphone),
                    text("04 March,1964"),
                    text(usersubject),
                    text("Reading"),
                    text("ScarletWitch.jpg"),
                    text(useraddress),
                    text(state),
                    text(city)
            );
        });

    }
}
