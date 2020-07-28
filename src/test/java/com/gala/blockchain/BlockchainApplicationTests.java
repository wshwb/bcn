package com.gala.blockchain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class BlockchainApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        String nyr = dateFormat.format(new Date());
        System.out.println(nyr);
    }

}
