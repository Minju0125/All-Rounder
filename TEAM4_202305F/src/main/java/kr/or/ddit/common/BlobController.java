package kr.or.ddit.common;

import java.io.BufferedInputStream;
import java.sql.Blob;

import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;


/**
 * @author 전수진
 * @since 2023. 11. 22.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 22.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public class BlobController {
	// [blob 데이터를 바이트로 변환해주는 메소드]
    public static byte[] blobToBytes(Blob blob) {
        BufferedInputStream is = null;
        byte[] bytes = null;
        try {
            is = new BufferedInputStream(blob.getBinaryStream());
            bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;

            while (offset < len
                    && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // [byte를 base64로 인코딩 해주는 메소드]
    public static String byteToBase64(byte[] arr) {
        String result = "";
        try {
            result = Base64Utils.encodeToString(arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
