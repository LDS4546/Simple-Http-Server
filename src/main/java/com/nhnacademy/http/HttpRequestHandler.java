/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.http;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
/* TODO#3 Java에서 Thread는 implements Runnable or extends Thread를 이용해서 Thread를 만들 수 있습니다.
*  implements Runnable을 사용하여 구현 합니다.
*/
public class HttpRequestHandler implements Runnable {
    private final Socket client;

    public HttpRequestHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        //TODO#4 simple-http-server-step1을 참고 하여 구현 합니다.
        /*
            <html>
                <body>
                    <h1>hello java</h1>
                </body>
            </html>
        */
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        while(true){
            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));)
            {
                log.debug("------HTTP-REQUEST_start()");
                while (true) {
                    String line = bufferedReader.readLine();
                    sb.append(line);
                    log.debug("{}", line);
                    if(Objects.isNull(line) || line.isBlank()){
                        break;
                    }
                }

                StringBuilder responseHeader = new StringBuilder();

                stringBuilder.append(String.format("<html>%s", System.lineSeparator()));
                stringBuilder.append(String.format("\t<body>%s", System.lineSeparator()));
                stringBuilder.append(String.format("\t\t<h1>hello java</h1>%s", System.lineSeparator()));
                stringBuilder.append(String.format("\t</body>%s", System.lineSeparator()));
                stringBuilder.append(String.format("</html>%s", System.lineSeparator()));

                responseHeader.append("HTTP/1.0 200 0K\r\n");

                responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));

                responseHeader.append(String.format("Content-type: text/html; charset=UTF-8%s",System.lineSeparator()));

                responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));


                responseHeader.append(String.format("Content-Length: %d%s", stringBuilder.length(), System.lineSeparator()));


                bufferedWriter.write(responseHeader.toString()+System.lineSeparator());
                bufferedWriter.write(stringBuilder.toString() + System.lineSeparator());
                bufferedWriter.flush();
            }
            catch (IOException e) {
                log.error("sock error : {}",e);
            }finally {
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }







    }
}
