package ru.pachan.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import ru.pachan.main.model.main.Certificate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
public class CertificateGraphQlControllerTest {

    @Autowired
    private GraphQlTester tester;

    @Disabled
    @Test()
    void addCertificate() {
        String query = """
                mutation {
                  newCertificate(certificateGraphQlDto: { code: "codeTest" }) {
                    id
                    code
                  }
                }""";
        Certificate certificate = tester.document(query)
                .execute()
                .path("data.newCertificate")
                .entity(Certificate.class)
                .get();
        Assertions.assertNotNull(certificate);
        Assertions.assertEquals("codeTest", certificate.getCode());
    }

    @Disabled
    @Test
    void findAll() {
        String query = """
                {
                  certificates {
                    id
                    code
                    person {
                      id
                      firstName
                    }
                  }
                }""";
        List<Certificate> certificates = tester.document(query)
                .execute()
                .path("data.certificates[*]")
                .entityList(Certificate.class)
                .get();
        Assertions.assertFalse(certificates.isEmpty());
        Assertions.assertNotNull(certificates.getFirst().getCode());
    }

    @Disabled
    @Test
    void findById() {
        String query = """
                  {
                  certificate(id: 1) {
                    id,
                    code
                  }
                }""";
        Certificate certificate = tester.document(query)
                .execute()
                .path("data.certificate")
                .entity(Certificate.class)
                .get();
        Assertions.assertNotNull(certificate);
        Assertions.assertNotNull(certificate.getCode());
    }

}