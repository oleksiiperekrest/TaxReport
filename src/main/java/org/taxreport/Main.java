package org.taxreport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.taxreport.dao.DaoPool;
import org.taxreport.dao.TaxReportDao;
import org.taxreport.dao.jdbc.DaoPoolImpl;
import org.taxreport.entity.*;
import org.taxreport.service.UserService;
import org.taxreport.service.impl.UserServiceImpl;
import org.taxreport.servlet.command.Command;
import org.taxreport.servlet.command.CreateReportCommand;
import org.taxreport.servlet.command.LoginCommand;
import org.taxreport.utils.JsonUtil;
import org.taxreport.utils.MD5Util;
import sun.security.provider.MD5;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        DaoPool daoPool = DaoPoolImpl.getInstance();
//        UserService userService = new UserServiceImpl(daoPool);
//        Client author = (Individual) userService.getByEmail("arnold.bawlson@gmail.com").get();
//        author.getReports().forEach( r -> System.out.println(r.getContent().toJson()));
        String login = "LoginCommand";

        String format = String.format("org.taxreport.servlet.command.%s", login);
        System.out.println(String.format("/jsp/%s.jsp", "login"));
        System.out.println(format);
        Command command1 = new CreateReportCommand();
        Command command11 = new LoginCommand();
//        Class aClass = Class.forName(format);
//        Command command2 = (Command) aClass.asSubclass(Command.class).newInstance();




//        Client author = new Individual(1L, "test@", "123", null, null, null, "0");
//        ObjectMapper objectMapper = new ObjectMapper();
////        String s = objectMapper.writeValueAsString(author);
////        System.out.println(s);
////        Client restored = objectMapper.readValue(s, Individual.class);
////        System.out.println(restored);
////        System.out.println(restored.getClass());
//
//        ReportContent content = new ReportContent(1200, 340, 20);
////        System.out.println(JsonUtil.getInstance().getObjectMapper().writeValueAsString(content));
//        System.out.println(content.toJson());
//        content.setTaxRate(18);
//        content.setComment("Lower");
//        System.out.println(content.toJson());
//        ReportContent reportContent = new ReportContent(content.toJson());
//        System.out.println(reportContent);
//        System.out.println(reportContent.toJson());


//        User author2 =  new LegalEntity(null, null, null, null, null, null);

//        Inspector inspector = new Inspector(2L, "e", "3", "bag", "soso");
//        inspector.getType()
//        LocalDateTime now = LocalDateTime.now();
//        TaxReport taxReport = new TaxReport(null, author, "Content", now, new ReportStatus("NEW"), inspector, now);
//        ConnectionPool connectionPool = new JdbcConnectionPool();
//        TaxReportDao taxReportDao = new TaxReportDaoImpl(connectionPool);
//        System.out.println("before: " + taxReport.getId());
//        taxReportDao.create(taxReport);
//        System.out.println("after: " + taxReport.getId());

//        DaoPool daoPool = new DaoPoolImpl();
////        TaxReportDao taxReportDao = daoPool.getTaxReportDao();
////        List<TaxReport> byClientId = taxReportDao.getByClientId(1L);
////        byClientId.forEach(System.out::println);

//        DaoPool daoPool = new DaoPoolImpl();
//        UserService userService = new UserServiceImpl(daoPool);
//
//        String email = "inspector.alice@taxreport.org";
//        String password = "5f4dcc3b5aa765d61d8327deb882cf99";
//        User user = userService.getByEmail(email).get();
//        if (userService.login(email, password)) {
//            System.out.println(user.getType());
//        }
//
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] digest = md5.digest();
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//
//        String password = "password";
//        System.out.println(MD5Util.getMd5(password));


    }

    private void getd(DaoPool daoPool) {

    }

    public static String getMd5(String input) throws NoSuchAlgorithmException {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
