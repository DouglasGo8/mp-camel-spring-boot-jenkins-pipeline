
def jarName
def firstDeploy = true
def serviceName = 'MyService'
def description = 'Java SpringBoot and Apache Camel'


pipeline {
    agent any
    tools {
        maven 'MAVEN_DEF'
    }
    stages {

        stage ('Git Clone') {
            steps {
                git branch: 'master', url: 'https://github.com/DouglasGo8/mp-camel-spring-boot-jenkins-pipeline.git'
            }
        }

        stage ('Compile State') {
            steps {
                sh 'mvn clean compile'
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
                script {
                    if (firstDeploy) {
                        jarName = findFiles(glob: 'target/*.jar')
                        writeFile file: serviceName + '.sh', text: wrapperJavaBashFile(serviceName, jarName[0].name)
                        writeFile file: serviceName + '.service', text: wrapperJavaLinuxService(serviceName)
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
Description =
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
