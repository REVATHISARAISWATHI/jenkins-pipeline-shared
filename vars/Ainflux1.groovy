def call ()
{
 sh """curl -i -w '%{http_code}' -X POST 'http://3.16.33.107:9000/api/measures/component?metricKeys=vulnerabilities&component=comrades.bmi%3ABMI'  --header 'Authorization: Basic YWRtaW46YWRtaW4= ' >test.txt"""

 def response =new File('/var/lib/jenkins/workspace/sonarnew/test.txt').text


 echo " ============ $response"

/*if(response == "200" || response == "204")
{
 echo "Data pushed into influxDB"
}
else
{
 error("Error while pushing")
}*/
}
