@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  import_hbase_csv startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and IMPORT_HBASE_CSV_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\import_hbase_csv-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\import_hbase_module-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\dao-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\gradle-api-5.0.jar;%APP_HOME%\lib\groovy-all-1.0-2.5.4.jar;%APP_HOME%\lib\gradle-installation-beacon-5.0.jar;%APP_HOME%\lib\entity-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\hbase-client-2.1.1.jar;%APP_HOME%\lib\hbase-hadoop2-compat-2.1.1.jar;%APP_HOME%\lib\hbase-hadoop-compat-2.1.1.jar;%APP_HOME%\lib\hbase-metrics-2.1.1.jar;%APP_HOME%\lib\hbase-metrics-api-2.1.1.jar;%APP_HOME%\lib\hbase-common-2.1.1.jar;%APP_HOME%\lib\spring-data-hadoop-boot-2.5.0.RELEASE.jar;%APP_HOME%\lib\spring-data-hadoop-config-2.5.0.RELEASE.jar;%APP_HOME%\lib\spring-data-hadoop-core-2.5.0.RELEASE.jar;%APP_HOME%\lib\hadoop-common-2.8.5.jar;%APP_HOME%\lib\hadoop-hdfs-2.8.5.jar;%APP_HOME%\lib\hadoop-mapreduce-client-jobclient-2.7.3.jar;%APP_HOME%\lib\hadoop-mapreduce-client-shuffle-2.7.3.jar;%APP_HOME%\lib\hadoop-mapreduce-client-common-2.7.3.jar;%APP_HOME%\lib\hadoop-mapreduce-client-core-2.7.3.jar;%APP_HOME%\lib\hadoop-yarn-client-2.7.3.jar;%APP_HOME%\lib\hadoop-yarn-server-nodemanager-2.7.3.jar;%APP_HOME%\lib\hadoop-yarn-server-common-2.7.3.jar;%APP_HOME%\lib\hadoop-yarn-common-2.7.3.jar;%APP_HOME%\lib\commons-io-2.6.jar;%APP_HOME%\lib\tools-0.0.1-SNAPSHOT.jar;%APP_HOME%\lib\commons-csv-1.6.jar;%APP_HOME%\lib\json-20180813.jar;%APP_HOME%\lib\spring-boot-starter-2.1.1.RELEASE.jar;%APP_HOME%\lib\hbase-protocol-shaded-2.1.1.jar;%APP_HOME%\lib\hbase-shaded-protobuf-2.1.0.jar;%APP_HOME%\lib\hbase-protocol-2.1.1.jar;%APP_HOME%\lib\hadoop-auth-2.8.5.jar;%APP_HOME%\lib\jets3t-0.9.0.jar;%APP_HOME%\lib\httpclient-4.5.6.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\commons-lang3-3.8.1.jar;%APP_HOME%\lib\metrics-core-4.0.3.jar;%APP_HOME%\lib\curator-recipes-2.7.1.jar;%APP_HOME%\lib\curator-framework-2.7.1.jar;%APP_HOME%\lib\curator-client-2.7.1.jar;%APP_HOME%\lib\zookeeper-3.4.10.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.25.jar;%APP_HOME%\lib\avro-1.7.4.jar;%APP_HOME%\lib\apacheds-kerberos-codec-2.0.0-M15.jar;%APP_HOME%\lib\apacheds-i18n-2.0.0-M15.jar;%APP_HOME%\lib\api-asn1-api-1.0.0-M20.jar;%APP_HOME%\lib\api-util-1.0.0-M20.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\hbase-shaded-miscellaneous-2.1.0.jar;%APP_HOME%\lib\hadoop-yarn-api-2.7.3.jar;%APP_HOME%\lib\protobuf-java-2.5.0.jar;%APP_HOME%\lib\hbase-shaded-netty-2.1.0.jar;%APP_HOME%\lib\htrace-core4-4.2.0-incubating.jar;%APP_HOME%\lib\joni-2.1.11.jar;%APP_HOME%\lib\jcodings-1.0.18.jar;%APP_HOME%\lib\commons-crypto-1.0.0.jar;%APP_HOME%\lib\jackson-databind-2.9.7.jar;%APP_HOME%\lib\audience-annotations-0.5.0.jar;%APP_HOME%\lib\hadoop-annotations-2.8.5.jar;%APP_HOME%\lib\guava-16.0.1.jar;%APP_HOME%\lib\commons-cli-1.2.jar;%APP_HOME%\lib\commons-math3-3.1.1.jar;%APP_HOME%\lib\xmlenc-0.52.jar;%APP_HOME%\lib\commons-net-3.1.jar;%APP_HOME%\lib\commons-configuration-1.6.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\servlet-api-2.5.jar;%APP_HOME%\lib\jetty-sslengine-6.1.26.jar;%APP_HOME%\lib\jetty-6.1.26.jar;%APP_HOME%\lib\jetty-util-6.1.26.jar;%APP_HOME%\lib\jsp-api-2.1.jar;%APP_HOME%\lib\jersey-json-1.9.jar;%APP_HOME%\lib\jersey-guice-1.9.jar;%APP_HOME%\lib\jersey-server-1.9.jar;%APP_HOME%\lib\jersey-client-1.9.jar;%APP_HOME%\lib\jersey-core-1.9.jar;%APP_HOME%\lib\commons-digester-1.8.jar;%APP_HOME%\lib\commons-beanutils-core-1.8.0.jar;%APP_HOME%\lib\commons-beanutils-1.7.0.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\jackson-jaxrs-1.9.13.jar;%APP_HOME%\lib\jackson-xc-1.9.13.jar;%APP_HOME%\lib\jackson-mapper-asl-1.9.13.jar;%APP_HOME%\lib\jackson-core-asl-1.9.13.jar;%APP_HOME%\lib\gson-2.8.5.jar;%APP_HOME%\lib\jsch-0.1.54.jar;%APP_HOME%\lib\jsr305-3.0.0.jar;%APP_HOME%\lib\commons-compress-1.4.1.jar;%APP_HOME%\lib\hadoop-hdfs-client-2.8.5.jar;%APP_HOME%\lib\commons-daemon-1.0.13.jar;%APP_HOME%\lib\netty-3.10.5.Final.jar;%APP_HOME%\lib\netty-all-4.1.31.Final.jar;%APP_HOME%\lib\xercesImpl-2.9.1.jar;%APP_HOME%\lib\leveldbjni-all-1.8.jar;%APP_HOME%\lib\spring-boot-autoconfigure-2.1.1.RELEASE.jar;%APP_HOME%\lib\spring-boot-2.1.1.RELEASE.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\spring-context-support-5.1.3.RELEASE.jar;%APP_HOME%\lib\spring-context-5.1.3.RELEASE.jar;%APP_HOME%\lib\spring-messaging-5.1.3.RELEASE.jar;%APP_HOME%\lib\spring-aop-5.1.3.RELEASE.jar;%APP_HOME%\lib\spring-beans-5.1.3.RELEASE.jar;%APP_HOME%\lib\spring-expression-5.1.3.RELEASE.jar;%APP_HOME%\lib\spring-core-5.1.3.RELEASE.jar;%APP_HOME%\lib\snakeyaml-1.23.jar;%APP_HOME%\lib\findbugs-annotations-1.3.9-1.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\jackson-annotations-2.9.0.jar;%APP_HOME%\lib\jackson-core-2.9.7.jar;%APP_HOME%\lib\nimbus-jose-jwt-4.41.1.jar;%APP_HOME%\lib\httpcore-4.4.10.jar;%APP_HOME%\lib\jettison-1.1.jar;%APP_HOME%\lib\jaxb-impl-2.2.3-1.jar;%APP_HOME%\lib\guice-servlet-3.0.jar;%APP_HOME%\lib\guice-3.0.jar;%APP_HOME%\lib\cglib-2.2.1-v20090111.jar;%APP_HOME%\lib\asm-3.1.jar;%APP_HOME%\lib\java-xmlbuilder-0.4.jar;%APP_HOME%\lib\paranamer-2.3.jar;%APP_HOME%\lib\snappy-java-1.0.4.1.jar;%APP_HOME%\lib\xz-1.0.jar;%APP_HOME%\lib\okhttp-2.4.0.jar;%APP_HOME%\lib\spring-jcl-5.1.3.RELEASE.jar;%APP_HOME%\lib\hadoop-streaming-2.7.3.jar;%APP_HOME%\lib\hadoop-distcp-2.7.3.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\jcip-annotations-1.0-1.jar;%APP_HOME%\lib\json-smart-2.3.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\okio-1.4.0.jar;%APP_HOME%\lib\accessors-smart-1.2.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\asm-5.0.4.jar

@rem Execute import_hbase_csv
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %IMPORT_HBASE_CSV_OPTS%  -classpath "%CLASSPATH%" cn.gov.cqaudit.big_reource.import_hbase_csv.CSVBoot %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable IMPORT_HBASE_CSV_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%IMPORT_HBASE_CSV_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
