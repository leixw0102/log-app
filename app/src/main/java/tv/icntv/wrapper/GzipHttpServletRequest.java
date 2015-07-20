package tv.icntv.wrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;


public class GzipHttpServletRequest extends HttpServletRequestWrapper {

        public GzipHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            request.setCharacterEncoding("utf-8");
        }

        /**
         * 返回GZIP格式的数据流对象
         */
        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new GzipInputStream((HttpServletRequest) getRequest());
        }

    }