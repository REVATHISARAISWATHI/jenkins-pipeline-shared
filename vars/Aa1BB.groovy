import groovy.json.*

def call(jsondata){
def jsonString = jsondata
def jsonObj = readJSON text: jsonString
String a=jsonObj.scm.projects.project.repositories.repository.repo_name
String repoName=a.replaceAll("\\[", "").replaceAll("\\]","");
String b=jsonObj.scm.projects.project.project_key 
String Key=b.replaceAll("\\[", "").replaceAll("\\]","");
int ecount = jsonObj.config.emails.email.size()
println("No of users "+ ecount)
println(Key)
println(repoName)
 withCredentials([usernamePassword(credentialsId: 'bitbucket_cred', passwordVariable: 'pass', usernameVariable: 'userId')]) {
  sh "curl -X GET  -H -d  -u $userId:$pass http://18.224.68.30:7990/rest/api/1.0/projects/'${Key}'/repos/'${repoName}'/pull-requests?state=all -o output.json"
 } 
def jsonSlurper = new JsonSlurper()
def resultJson = jsonSlurper.parse(new File("/var/lib/jenkins/workspace/${JOB_NAME}/output.json"))
def total = resultJson.size
 echo "Total no.of Pull reqests ${repoName} $total"
//def commiter=1
//List<String> JSON = new ArrayList<String>();
	
def jsonBuilder = new groovy.json.JsonBuilder()

jsonBuilder.bitbucket(
  "total_pullrequests": resultJson.values,
 "pullreq_count": resultJson.size
)
File file = new File("/var/lib/jenkins/workspace/${JOB_NAME}/pulls.json")
file.write(jsonBuilder.toPrettyString())
//values	
 }
