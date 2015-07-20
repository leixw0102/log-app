package tv.icntv.servlet;/*
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
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.utils.FileUtils;
import tv.icntv.utils.MacUtils;
import tv.icntv.wrapper.GzipHttpServletRequest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2015/07/14
 * Time: 11:02
 */
public class ReceiveServlet extends HttpServlet {
    Path p=null;
    @Override
    public void init(ServletConfig config) throws ServletException {
//
        String path =config.getInitParameter("path");
        p = Paths.get(path);
        if(!Files.exists(p, LinkOption.NOFOLLOW_LINKS)){
            try {
                Files.createDirectory(p);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        try {

            if(req instanceof GzipHttpServletRequest){
                GzipHttpServletRequest request= (GzipHttpServletRequest) req;
                String icntvMac=request.getHeader("id");
                String fileName= MacUtils.long2Mac(Long.parseLong(icntvMac))+".log";
                fileName = fileName.replace(":","");
                String day=DateTime.now().toString("yyyy-MM-dd");

                //按天生成目录
                Path todayPath=Paths.get(p.toString(),day);
                if(!Files.exists(todayPath)){
                    Files.createDirectories(todayPath);
                }

                Path path=Paths.get(todayPath.toString(),fileName);
                if(!Files.exists(path)){
                    Files.createFile(path);
                }
                logger.debug("file name ={},path={}",fileName,path.toString());
                String msg = FileUtils.getString(request.getInputStream());
                if(Strings.isNullOrEmpty(msg)){

                } else {
                    FileUtils.write(msg,path.toString());
                }
                returnInfo(resp,"success");
            }

        } catch (Exception e){
            logger.error("msg error!",e);
            try {
                returnInfo(resp,"error!"+e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void returnInfo(HttpServletResponse response,String msg) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(msg);
        writer.flush();
        writer.close();
    }

    public static void main(String[]args) throws IOException {
//        String day=DateTime.now().toString("yyyy-MM-dd");
//        Path path=Paths.get("d:\\",day,"abc.log");
//        System.out.println(path.toString());
//        if(!Files.exists(path)){
//            Files.(path);
//        }
    }
}
