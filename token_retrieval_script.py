import requests
import json
import time

# Keycloak configuration
KEYCLOAK_URL = "https://lemur-14.cloud-iam.com/auth/realms/teamnovauom/protocol/openid-connect/token"
CLIENT_ID = "Health-Hive-Client"
# CLIENT_SECRET = "wR9CfQqsolEktMiZlARq5FXDXPcNxJ9a"
USERNAME = "teamnova.uom@gmail.com"
PASSWORD = "12345"
TOKEN_FILE = "./app/tokens/token.txt"

def retrieve_token():
    retry_attempts = 10  # Number of retry attempts
    retry_delay = 60  # Delay in seconds before retrying

    for attempt in range(retry_attempts):
        try:
            data = {
                'grant_type': 'password',
                'client_id': CLIENT_ID,
                # 'client_secret': CLIENT_SECRET,
                'username': USERNAME,
                'password': PASSWORD
            }

            response = requests.post(KEYCLOAK_URL, data=data)

            if response.status_code == 200:
                token_info = response.json()
                access_token = token_info.get('access_token')
                if access_token:
                    with open(TOKEN_FILE, 'w') as token_file:
                        token_file.write(access_token)
                    print("Token retrieved successfully")
                    return  # Exit the function if token retrieval is successful
                else:
                    print("Access token not found in response")
            else:
                print("Failed to retrieve token:", response.text)

        except requests.exceptions.RequestException as e:
            print(f"Communication error occurred: {e}")
            if attempt < retry_attempts - 1:
                print(f"Retrying in {retry_delay} seconds...")
                time.sleep(retry_delay)

def renew_token():
    # Implement token renewal logic if needed
    # This might involve refreshing the token before it expires
    pass

def main():
    while True:
        retrieve_token()
        renew_token()  # Implement token renewal logic
        time.sleep(300)  # Sleep for an 5 minutes before retrieving token again

if __name__ == "__main__":
    main()
