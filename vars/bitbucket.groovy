def call(){

sh 'curl  -X POST --user Megalai:Mumani1209@98 "https://api.bitbucket.org/2.0/repositories/Megalai/sample" -d {"scm": "git", "website": null, "has_wiki": false, "name": "sample"}'
}

      

