package com.awan.securityjwt;

import com.awan.securityjwt.model.entity.UserLDAP;
import com.awan.securityjwt.repository.UserLDAPRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@SpringBootTest()
public class LDAPTest {


    @Autowired
    UserLDAPRepository repository;

    @Autowired
    ResourceLoader resourceLoader;


    @Test
    void testRepo() throws IOException {
        UserLDAP yuyunPurniawan = repository.findByUsername("ypurniawan").get();
        System.out.println(yuyunPurniawan);

//        saveObj(yuyunPurniawan);
    }

    @Test
    void testRead() throws IOException {

        UserLDAP userLDAP = readObj(UserLDAP.class);
        byte[] password = userLDAP.getPassword();

        System.out.println(new String(password));

    }

    void saveObj(Object object) throws IOException {
        String fileName = "ldap";
        Resource resource = resourceLoader.getResource("classpath:/objects/");
        Path dirPath = Paths.get(resource.getURI());

        Path filePath = Paths.get(dirPath.toString(), fileName + ".obj");
        System.out.println(filePath);
        Files.write(
                filePath,
                "".getBytes(),
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
        );

        try (
                OutputStream outputStream = Files.newOutputStream(filePath);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ) {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    <T> T readObj(Class<T> tClass) throws IOException {
        String fileName = "ldap";
        Resource resource = resourceLoader.getResource("classpath:/objects/" + fileName + ".obj");
        Path filePath = Paths.get(resource.getURI());

        try (
                InputStream inputStream = Files.newInputStream(filePath);
                ObjectInputStream objectOutputStream = new ObjectInputStream(inputStream);
        ) {
            return (T) objectOutputStream.readObject();
        } catch (Exception exception) {
            return null;
        }
    }
}
