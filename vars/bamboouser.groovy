import groovy.json.*
import groovy.json.JsonOutput

def call(JSON,rig)
{
	
def jsonString = JSON
def jsonObj = readJSON text: jsonString
def mailcount = jsonObj.riglet_info.auth_users.size()
def key= jsonObj.ci.projectplankey.key
def jsonObja = readJSON text: rig
def IP=jsonObja.url
def username=jsonObja.userName
def password=jsonObja.password
//String sresponse = sh(script: """curl  -X GET -L -w '%{url_effective} %{http_code}\\n' -u ${username}:${password} '${IP}/rest/api/latest/result/${key}.json?max-result=50&expand=results.result.artifacts&expand=changes.change.files&start-index=0'  """, returnStdout: true)
//println(sresponse)
	
	//JSON.stringify(sresponse)
	//println(sresponse) 
	//def r= readJSON text: sresponse
	/*String res=sresponse.split('200')
	println(res[1])
	println("hi")
	println(res[2])*/
	
	def response = sh(script: """curl  -X GET -L -w '%{http_code}\\n' -u ${username}:${password} '${IP}/rest/api/latest/result/${key}.json?max-result=50&expand=results.result.artifacts&expand=changes.change.files&start-index=0' -o outputbamboo.json """, returnStdout: true)
//println(response) 
	
	

// }
	
	//println(response)
	try
	{
	
	def jsonSlurper = new JsonSlurper()
def reader = new BufferedReader(new InputStreamReader(new FileInputStream("/var/lib/jenkins/workspace/${JOB_NAME}/outputbamboo.json"),"UTF-8"))
def resultJson = jsonSlurper.parse(reader)
	//def resultJson= readJSON text: response
	def bno=resultJson.results.result[0].buildNumber
	//println(bno)


 


  List<String> USERS = new ArrayList<String>()
	List<String> USERF = new ArrayList<String>()
 List<String>  LISTSUCCESS=new ArrayList<String>()
	 List<String>  LISS=new ArrayList<String>()
	 List<String>  LISF=new ArrayList<String>()
	List<String> LISTFAILURE=new ArrayList<String>()
	List<String> SUCCESS = new ArrayList<String>()
    List<String> FAILURE = new ArrayList<String>()
	 List<String> UNKNOWN = new ArrayList<String>()
	List<String> USERI = new ArrayList<String>()
 List<String>  LISI=new ArrayList<String>()
	 List<String>  LISTIN=new ArrayList<String>()
	
	


 


	def jsonBuilder = new groovy.json.JsonBuilder()

   for(j=0;j<mailcount;j++)
   {
	   def cns=0
	   def cnf=0
	    def cni=0
	  
    def email=jsonObj.riglet_info.auth_users[j] 
  for(i=0;i<bno;i++)
  {
 
   
   def state=resultJson.results.result[i].buildState
	 // println(state)
  
   if(resultJson.results.result[i].buildReason.contains(email) && state.equals("Successful"))
   {
   
    USERS.add(resultJson.results.result[i])
	  
   }
   else if(resultJson.results.result[i].buildReason.contains(email) && state.equals("Failed"))
   {
	   
	   USERF.add(resultJson.results.result[i])
   }
	   if(resultJson.results.result[i].buildReason.contains(email) )
   {
	   
	   USERI.add(resultJson.results.result[i])
   }
   }
   cns=USERS.size()

	
	   LISS[j]=USERS.clone()
	   LISF[j]=USERF.clone()
	   LISI[j]=USERI.clone()
	   
   LISTSUCCESS.add(["email":email,"success":LISS[j],"Success_cnt":cns])
   USERS.clear()
	 
   cnf=USERF.size()
   LISTFAILURE.add(["email":email,"failure":LISF[j],"Failure_cnt":cnf])
   USERF.clear()
	   cni=USERI.size()
   LISTIN.add(["email":email,"total":LISI[j],"totalBuilds":cni])
   USERI.clear()
	  
	   
   }
	for(i=0;i<bno;i++)
  {
  // def date=resultJson.results.result[i].buildCompletedDate
   def state=resultJson.results.result[i].buildState
	  //println("hi")
	// println(state)

   
  if(state.equals("Successful"))
  {
   
 
   SUCCESS.add(resultJson.results.result[i])
     
  }
   else if(state.equals("Failed"))
   {
    
       FAILURE.add(resultJson.results.result[i])
     
   }
  }
	
		    jsonBuilder.Bamboo(
			     "totalBuilds" :bno,
  "teamsuccess" : SUCCESS,
  "teamsuccessbuild_cnt" : SUCCESS.size(),
  "teamfailure" : FAILURE,
  "teamfailurebuild_cnt" :FAILURE.size(),
  "individualsuccess": LISTSUCCESS,
  "individualfailure": LISTFAILURE,
   "individualtotal":LISTIN
  )
	
//File file = new File("/var/lib/jenkins/workspace/${JOB_NAME}/bamboo.json")
//file.write(jsonBuilder.toPrettyString())
	//def reader1 = new BufferedReader(new InputStreamReader(new FileInputStream("/var/lib/jenkins/workspace/${JOB_NAME}/bamboo.json"),"UTF-8"))
//def resu = jsonSlurper.parse(reader1)

	//println(jsonBuilder)
//jsonBuilder =jsonBuilder.Stringify()
		
	return jsonBuilder
	

}
	
catch(Exception e)
{
	//println(response)
	e.printStackTrace()
	
}
	 finally{
		//println(response)
		
		if(response.contains("200"))
		{
		println("data collected scuccesslfully")	
		
		}
	if(response.contains("404"))
	println("Not found")
	if(response.contains("400"))
	println("Bad Request")
        if(response.contains("401"))
	println("Unauthorized")
	if(response.contains("403"))
		println("Forbidden")
	if(response.contains("500"))
		println("Internal Server Error")
		 }
		
	
	
		
	
}
