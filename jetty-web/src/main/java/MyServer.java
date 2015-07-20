/*
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

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2015/07/17
 * Time: 10:25
 */
public class MyServer {
    private static Logger logger = LoggerFactory.getLogger(MyServer.class);

    static Server server;

    static {
        try {
            Resource resource = Resource.newSystemResource("jetty.xml");
            XmlConfiguration configuration = new XmlConfiguration(resource.getInputStream());
            server = (Server) configuration.configure();
        } catch (Exception e) {
            logger.error("MyServer initail error ", e);
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        server.start();
        server.join();
    }

    public static void start() throws Exception {
        server.start();
    }

    public static void stop() throws Exception {
        server.stop();
    }
}
