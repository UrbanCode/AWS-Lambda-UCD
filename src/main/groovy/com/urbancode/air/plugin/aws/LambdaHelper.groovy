/**
 * (c) Copyright IBM Corporation 2017.
 * This is licensed under the following license.
 * The Eclipse Public 1.0 License (http://www.eclipse.org/legal/epl-v10.html)
 * U.S. Government Users Restricted Rights:  Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package com.urbancode.air.plugin.aws

import com.urbancode.air.CommandHelper

public class LambdaHelper {

    public static final String AWS_PROFILE = "AWS_PROFILE"
    public static final String AWS_ACCESS_KEY_ID  = "AWS_ACCESS_KEY_ID "
    public static final String AWS_SECRET_ACCESS_KEY  = "AWS_SECRET_ACCESS_KEY "

    CommandHelper ch

    public LambdaHelper() {
        this.ch = new CommandHelper(new File("."))
    }

    public LambdaHelper(String awsProfile, String accessKey, String secretKey) {
        this.ch = new CommandHelper(new File("."))
        if (awsProfile) {
            ch.addEnvironmentVariable(AWS_PROFILE, awsProfile)
        }
        if (accessKey) {
            ch.addEnvironmentVariable(AWS_ACCESS_KEY_ID, accessKey)
        }
        if (secretKey) {
            ch.addEnvironmentVariable(AWS_SECRET_ACCESS_KEY, secretKey)
        }
    }

    // Verify that aws cli exists
    private List<String> getAWSLambdaCli() {
        return ["aws", "lambda"]
    }

    private int runCommand(String message, List<String> command) {
        int result
        command = getAWSLambdaCli() + command
        result = ch.runCommand(message, command)
        return result
    }
}
