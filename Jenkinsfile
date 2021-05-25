
def jarName
def firstDeploy = false
def serviceName = 'MyService'


pipeline {
    agent any
    tools {
        maven 'MAVEN_DEF'
    }
    environment {
        SERVER_PWD = credentials('ssh_server_test_pwd')
    }
    stages {
        stage ('Git Clone') {
            steps {
                git branch: 'master', url: 'https://github.com/DouglasGo8/mp-camel-spring-boot-jenkins-pipeline.git'
            }
        }
        stage ('Build app') {
            steps {
                sh 'mvn -DskipTests clean compile package'
            }
        }
        stage ("Write Wrapper Service") {
            steps {
                // sh 'pwd'
                // https://www.jenkins.io/doc/pipeline/steps/ssh-steps/
                script {
                    def remote = [:]
                    remote.name = 'test'
                    remote.host = 'lpvdocker01.domhcor.local'
                    remote.user = 'devguest'
                    remote.password = env.SERVER_PWD
                    remote.allowAnyHosts = true
                        if (firstDeploy) {
                            jarName = findFiles(glob: 'target/*.jar')
                            shellScript = serviceName + '.sh'
                            linuxService = serviceName + '.service'
                            writeFile file: shellScript, text: wrapperJavaBashFile(serviceName, jarName[0].name)
                            writeFile file: linuxService, text: wrapperJavaLinuxService(serviceName)
                            targetJar = 'target/' + jarName[0].name
                            // ***************************************************************
                            // COPY .jar|.sh|.service to home ON TARGET_SERVER
                            // *****************************************************************
                            sshPut remote: remote, from: targetJar, into: '.', override: true
                            sshPut remote: remote, from: shellScript, into: '.', override: true
                            sshPut remote: remote, from: linuxService, into: '.', overide: true
                            // ***************************************************************
                            // MOVE .jar|.sh|.service to dirs ON TARGET_SERVER
                            // *****************************************************************
                            sshCommand remote: remote, command: 'mv *.jar *.sh /usr/local/bin', sudo: true
                            sshCommand remote: remote, command: 'mv *.service /etc/systemd/system', sudo: true
                            sshCommand remote: remote, command: 'chmod +x /usr/local/bin/' + shellScript, sudo: true
                            sshCommand remote: remote, command: 'systemctl start ' + linuxService, sudo: true
                        } else {
                            echo 'SSH Command Only'

                        }

                }
            }
        }
    }

    /*post {
        always {
            sh 'rm -r *'
        }
    }*/
}

/**
 * @see  http://www.jcgonzalez.com/linux-java-service-wrapper-example
 */
def wrapperJavaBashFile (serviceName, jarName) {
    return ("""#!/bin/sh
SERVICE_NAME=${serviceName}
Logging=/var/log/log_\$SERVICE_NAME.log
PATH_TO_JAR=/usr/local/bin/${jarName}
PID_PATH_NAME=/tmp/${serviceName}-pid
case \$1 in
start)
    echo "Starting \$SERVICE_NAME ..." >> \$Logging
    if [ ! -f \$PID_PATH_NAME ]; then
        nohup java -XX:+UseParallelGC -XX:+AggressiveHeap -XX:ParallelGCThreads=4 -XX:MaxHeapSize=2G -XX:MaxMetaspaceSize=2G -XX:+OptimizeStringConcat -XX:+UseStringDeduplication -XX:+HeapDumpOnOutOfMemoryError -jar \$PATH_TO_JAR /tmp 2>> /dev/null >>/dev/null &
        echo \$! > \$PID_PATH_NAME
        echo "\$SERVICE_NAME started ..." >> \$Logging
    else
        echo "\$SERVICE_NAME is already running ..." >> \$Logging
    fi
    ;;
stop)
    if [ -f \$PID_PATH_NAME ]; then
        PID=\$(cat \$PID_PATH_NAME);
        echo "\$SERVICE_NAME stoping ..." >> \$Logging
        kill \$PID;
        echo "\$SERVICE_NAME stopped ..." >> \$Logging
        rm \$PID_PATH_NAME
    else
        echo "\$SERVICE_NAME is not running ..." >> \$Logging
    fi
    ;;
restart)
    if [ -f \$PID_PATH_NAME ]; then
        PID=\$(cat \$PID_PATH_NAME);
        echo "\$SERVICE_NAME stopping ..." >> \$Logging;
        kill \$PID;
        echo "\$SERVICE_NAME stopped ..." >> \$Logging;
        rm \$PID_PATH_NAME
        echo "\$SERVICE_NAME starting ..." >> \$Logging
        nohup java -jar \$PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
        echo \$! > \$PID_PATH_NAME
        echo "\$SERVICE_NAME started ..." >> \$Logging
    else
        echo "\$SERVICE_NAME is not running ..." >> \$Logging
    fi
    ;;
esac
""")
}

/**
 *
 */
def wrapperJavaLinuxService (serviceName) {
    return ("""
[Unit]
Description = Java SpringBoot and Apache Camel
After network.target = ${serviceName}.service
[Service]
Type = forking
Restart=always
RestartSec=1
SuccessExitStatus=143
ExecStart =  /usr/local/bin/${serviceName}.sh start
ExecStop = /usr/local/bin/${serviceName}.sh stop
ExecReload = /usr/local/bin/${serviceName}.sh reload
[Install]
WantedBy=multi-user.target
""")
}

// Pipeline Utility Steps
