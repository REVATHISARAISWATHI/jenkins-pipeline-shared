import groovy.json.*


@NonCPS
create(){
  def jsonSlurper = new JsonSlurper()
  def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/output.json"))
def total = resultJson.size
  echo "$total"
//def value=resultJson.values.author[0].name
  //echo "$value"

  
  
// Long date= System.currentTimeMillis() 
 //echo "$date"
  
  
  def count=0
  //
 
 for(i=0;i<total;i++)
 {
    Long timer=resultJson.values.committerTimestamp[i]
   
   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
sdf.format(new Date(timer));
   
   
  // Date date=$($timer / 1000 +%Y-%m-%d)
   echo "$sdf"
Date today_date = new Date() 
   echo "$today_date"
  // Long sub=Math.subtractExact(date,timer)
  //echo "$sub"
   //if(resultJson.values.committerTimestamp[i]==1582522990000)
   if (resultJson.values.author[i].name=="rig" && date==today_date)
   {
    count ++
   
   }
 }
 echo "$count" 
 
}
def call()
{
create()
}
