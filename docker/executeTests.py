import requests
import json
import sys

resp = requests.get("http://dock-qa-se-02:8084/features")
data = json.loads(resp.content)

test_success = True

for elems in data:
    for elementArray in elems['elements']:
        for step in elementArray['steps']:
            if step['result']['status'] == 'failed':
                print("Scenario: " + format(json.dumps(elementArray['name'], indent=4)))
                print(" -> StepName: " + format(json.dumps(step['name'], indent=4)) + " --> Result: " + format(json.dumps(step['result']['status'], indent=4)))
                test_success = False

if not test_success:
    print ("ERROR: Test failed");
    print ("\nDUMP: " + format(json.dumps(data, indent=4)))
    sys.exit(1)









