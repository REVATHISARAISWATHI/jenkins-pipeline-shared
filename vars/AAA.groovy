import groovy.json.*

import java.time.Instant
import java.util.Date
@NonCPS
create(){
  def jsonSlurper = new JsonSlurper()
  def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/output.json"))
def total = resultJson.size
  echo "$total"
def value=resultJson.values.author[0].name
  echo "$value"
 String timer=resultJson.values.committerTimestamp[0]
  echo "$timer"
  
  def theDay=new Date(timer)
def today=new Date

if($theDay==$today)
  { echo "same day" }
  def count=0
  //
 for(i=0;i<total;i++)
 {
   //if(resultJson.values.committerTimestamp[i]==1582522990000)
   if (resultJson.values.committerTimestamp[i]==date)
   {
    count ++
   echo "$count"
   }
 }
  
 
}
def call()
{
create()
}
