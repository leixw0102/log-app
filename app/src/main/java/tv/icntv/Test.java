package tv.icntv;/*
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

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2015/07/16
 * Time: 10:09
 */
public class Test {
    public static long bytes2long(byte[] b) {
        long temp = 0;
        long res = 0;
        for (int i=0;i<8;i++) {
            res <<= 8;
            temp = b[i] & 0xff;
            res |= temp;
        }
        return res;
    }

    public static byte[] long2bytes(long num) {
        byte[] b = new byte[8];
        for (int i=0;i<8;i++) {
            b[i] = (byte)(num>>>(56-(i*8)));
        }
        return b;
    }

    public static byte [] getMacBytes(String mac){
        byte []macBytes = new byte[8];
        String [] strArr = mac.split(":");

        for(int i = 0;i < strArr.length; i++){
            int value = Integer.parseInt(strArr[i],16);
            macBytes[i] = (byte) value;
        }
        return macBytes;
    }

    public static void main(String[]args){
//        String value = "";
//        for(int i = 0;i < macBytes.length; i++){
//            String sTemp = Integer.toHexString(0xFF &  macBytes[i]);
//            value = value+sTemp+":";
//        }
//
//        value = value.substring(0,value.lastIndexOf(":"));
//        System.out.println("value="+value)
        long id = bytes2long(getMacBytes("12:34:56:78:90:12"));
        System.out.println(15159014232897L^10000L);
         System.out.println(id);
    }
}
