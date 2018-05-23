package com.fxl.client.hisWS;

public class WebserviceTest {

    public static void main(String[] args) {
        try {
            String wsdlUrl = "http://121.18.88.186:9181/EXTERNALSERVICES/ZL_INFORMATIONSERVICE.ASMX?WSDL";
            String endPoint = "http://121.18.88.186:9181/EXTERNALSERVICES/ZL_INFORMATIONSERVICE.ASMX";
            String nameSpaceUri = "http://tempuri.org/";
            WebService ws = new WebService(wsdlUrl, endPoint, nameSpaceUri);
            
            String hisMethod = "UserManager";
            String[] paramsObj = {
                    "<ROOT>" +
                        "<TOKEN>GISbh1fZ1elBpQI2fD/nfkmyfGc+55BSMlav8iX3QbY9qPKKtrRPIGKczE0grsr3</TOKEN>" +
                        "<SERVICE>BindCard.UserInfoByRegNO.Query</SERVICE>" +
                        "4QS79v+YxOmKNmSvoSDi2YFbMa1Tqcm5B7XlIJBP+dEIsBVOGmbL+NmuHAg8o8EYYvJ2ijeiAXKR3dYtc9AHqJ6V80g8h+7x3O2akRLSR69BIbGnUoZGv0MwzA4DIK8YUVRApmL+7O2QdqES2bSkIz0MDcF5v060r2h0q5JQGP2z6uGdMEroWa8aaQknkn++NBbmLUOSztw1cdofRCt5cOy9bprXEp1hDl53Yv5UUCY=</DATAPARAM>" +
                    "</ROOT>"
            };
            String[] fileds = {};
            String result = ws.getResponseXML(hisMethod, paramsObj, fileds);
            
            System.out.println(result);
            
            
            /*String wsdlUrl = "http://10.1.93.101:9180/hdepc/services/hisWebService?wsdl";
            String endPoint = "http://10.1.93.101:9180/hdepc/services/hisWebService";
            String nameSpaceUri = "http://service.bd.com/";
            WebService ws = new WebService(wsdlUrl, endPoint, nameSpaceUri);
            
            String hisMethod = "requestWS";
            String[] paramsObj = {"<request><head><key>queue_common_bindCard</key><hospcode>100002050</hospcode><token>权限验证</token><time>时间如20130901150401</time></head><body><card_no>362427199307270811</card_no><name>小方</name><sex></sex><card_Type>1</card_Type><birthDay></birthDay></body></request>"};
            String[] fileds = {};
            String result = ws.getResponseXML(hisMethod, paramsObj, fileds);
            
            System.out.println(result);*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}