package tv.icntv.wrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
     * 自定义输入流，采用GZIP方式来读取数据
     */
    public class GzipInputStream extends ServletInputStream {

        private GZIPInputStream stream;

        public GzipInputStream(HttpServletRequest req) throws IOException {
            this.stream = new GZIPInputStream(req.getInputStream());
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return stream.read(b, off, len);
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }

    }
