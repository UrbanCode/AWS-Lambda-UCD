/**
 * (c) Copyright IBM Corporation 2017.
 * This is licensed under the following license.
 * The Eclipse Public 1.0 License (http://www.eclipse.org/legal/epl-v10.html)
 * U.S. Government Users Restricted Rights:  Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

 // http://docs.aws.amazon.com/cli/latest/reference/lambda/create-function.html

import com.urbancode.air.AirPluginTool
import com.urbancode.air.ExitCodeException
import com.urbancode.air.plugin.aws.LambdaHelper

AirPluginTool apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties()

String functionName = props["functionName"].trim()
String runtime      = props["runtime"].trim()
String role         = props["role"].trim()
String handler      = props["handler"].trim()
String zipFile      = props["zipFile"].trim()
def otherArgs       = props["otherArgs"].split("\n|=")*.trim()

String profile = props["profile"].trim()
String accessKey = props["accessKey"].trim()
String secretKey = props["secretKey"]

println "Other Args: " + otherArgs.toString()

def command = ["create-function"]

command << "--function-name"
command << functionName

command << "--runtime"
command << runtime

command << "--role"
command << role

command << "--handler"
command << handler

if (zipFile) {
    if (new File(zipFile).isFile()) {
        command << "--zip-file"
        // fileb:// required by syntax
        command << "fileb://" + zipFile
    } else {
        throw new RuntimeException("[Error] Zip file `${zipFile}` does not exist. ")
    }
}
command += otherArgs

LambdaHelper lh = new LambdaHelper(profile, accessKey, secretKey)
int exitCode = lh.runCommand("Creating function...", command)

println "Exit Code: " + exitCode


println "Successfully created the '${functionName}' Lambda function."
