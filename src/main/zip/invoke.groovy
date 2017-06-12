/**
 * (c) Copyright IBM Corporation 2017.
 * This is licensed under the following license.
 * The Eclipse Public 1.0 License (http://www.eclipse.org/legal/epl-v10.html)
 * U.S. Government Users Restricted Rights:  Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

 // http://docs.aws.amazon.com/cli/latest/reference/lambda/invoke.html

import com.urbancode.air.AirPluginTool
import com.urbancode.air.ExitCodeException
import com.urbancode.air.plugin.aws.LambdaHelper

AirPluginTool apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties()

String functionName = props["functionName"].trim()
String outfile      = props["outfile"].trim()?:"outfile.txt"
def otherArgs       = props["otherArgs"].split("\n|=")*.trim()

String profile = props["profile"].trim()
String accessKey = props["accessKey"].trim()
String secretKey = props["secretKey"]

println "Other Args: " + otherArgs.toString()

def command = ["invoke"]

command << "--function-name"
command << functionName

command += otherArgs

command << outfile
apTool.setOutputProperty("outfile", outfile)
apTool.setOutputProperties()

LambdaHelper lh = new LambdaHelper(profile, accessKey, secretKey)
int exitCode = lh.runCommand("Invoking function...", command)

apTool.setOutputProperties()
println "Successfully invoked the '${functionName}' Lambda function."
