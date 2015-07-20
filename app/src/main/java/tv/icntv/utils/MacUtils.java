package tv.icntv.utils;/*
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

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

import java.util.List;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2015/07/16
 * Time: 11:46
 */
public class MacUtils {

    public static Long mac2Long(String mac){
        if(Strings.isNullOrEmpty(mac)){
            return 0L;
        }

        String[] macTemp = mac.split(":");
        if(macTemp.length!=6){
            return -1L;
        }
        Long num=0L;
        for(int i=0;i<6;i++){
            num +=Long.parseLong(macTemp[i], 16)<<(8*(6-1-i));
        }
        return num;
    }

    public static String long2Mac(Long l){
        String[] macTemp = new String[6];
        Long x=l;
        for(int i=0;i<6;i++){
            long temp=  (x>>(8*(6-1-i)));
            macTemp[i]=Long.toHexString(temp);
            if(temp<16){
                macTemp[i]="0"+ macTemp[i];
            }

            x-=temp<<(8*(6-1-i));
        }
        return Joiner.on(":").join(macTemp);
    }

    public static void main(String[]args){
        System.out.println(long2Mac(15617951830034L));
        System.out.println(mac2Long("0e:34:56:78:90:12"));
    }
}
