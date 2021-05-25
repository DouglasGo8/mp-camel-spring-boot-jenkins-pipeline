# Jenkinsfile Devops HCOR Steps

---

* Install Plugins Pipeline Utility Steps
* SSHCommand with Plugin SSH Pipeline Steps 

[SSH](https://jaehoo.wordpress.com/2019/12/20/jenkins-execute-script-over-ssh-with-pipeline/)
sshUserPrivateKey
https://issues.jenkins.io/browse/JENKINS-57269?page=com.atlassian.jira.plugin.system.issuetabpanels%3Aall-tabpanel

1. sudo mkdir /var/log
2. sudo mv .sh /usr/local/bin/.sh
3. sudo mv .service /etc/systemd/system/.service
4. sudo systemctl daemon-reload
5. sudo systemctl {{Service}} start