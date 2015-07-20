package tv.icntv.filter;/*
 * Copyright 2015 Future TV, Inc.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.icntv.tv/licenses/LICENSE-1.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.utils.MacUtils;
import tv.icntv.wrapper.GzipHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2015/07/14
 * Time: 11:02
 */
public class UserFilter implements Filter {
    private String mCharset;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DEFAULT_CHARSET = "utf-8";
    private String ID="id";
    private String UK="uk";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String charset = filterConfig.getInitParameter("charset");
        if (Strings.isNullOrEmpty(charset) || !Charset.isSupported(charset)) {
            mCharset = DEFAULT_CHARSET;
        } else {
            mCharset = charset;
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String ip = request.getRemoteAddr();
        if(isGzipSupported(request)){
            GzipHttpServletRequest gReq=new GzipHttpServletRequest(request);
            String id=gReq.getHeader(ID);
            String uk =gReq.getHeader(UK);

            logger.info("id={},uk={},ip={}",id,uk,ip);
            if(Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(uk)){
                logger.error("id or uk is null ;ip={}",ip);
                PrintWriter writer=response.getWriter();
                writer.println("header error!");
                writer.flush();
                writer.close();
                return;
            }
            Long ukLong = Long.parseLong(uk);
            Long idLong=Long.parseLong(id);
            if((ukLong^10000L)==idLong){
                chain.doFilter(gReq,response);
                return;
            }
            logger.error("illegal request!ip={},id={},uk={}",ip,id,uk);
            PrintWriter writer=response.getWriter();
            writer.println("illegal request!");
            writer.flush();
            writer.close();
            return;

        }  else {
            logger.error("error request! not gz ,ip={}",ip);
            PrintWriter writer=response.getWriter();
            writer.println("please used gz inputstream");
            writer.flush();
            writer.close();
        }

    }
    /**
     * 检测客户端是否支持GZIP格式编码
     */
    private boolean isGzipSupported(HttpServletRequest request) {
        String encoding = request.getHeader("accept-encoding");
        logger.info(encoding);
        return (!Strings.isNullOrEmpty(encoding) && (encoding.indexOf("gzip") != -1));
    }
    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }




}
