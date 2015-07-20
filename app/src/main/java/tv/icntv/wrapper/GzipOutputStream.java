package tv.icntv.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
     * 自定义输出流，采用GZIP方式往客户端返回数据
     */
  public   class GzipOutputStream extends ServletOutputStream {

        private GZIPOutputStream stream;

        public GzipOutputStream(HttpServletResponse resp) throws IOException {
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("text/json;charset=".concat("utf-8"));
            resp.setHeader("content-encoding", "gzip");

            this.stream = new GZIPOutputStream(resp.getOutputStream());
        }

        @Override
        public void write(int b) throws IOException {
            stream.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            stream.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            stream.flush();
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }

    }