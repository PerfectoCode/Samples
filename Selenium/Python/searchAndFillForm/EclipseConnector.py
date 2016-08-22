# ported from package: com.perfectomobile.selenium.util.EclipseConnector
import socket


class EclipseConnector:
    def __init__(self):
        pass

    LOCALHOST = "localhost"
    PORT = 3287
    GET_HOST = "getHost"
    GET_USER = "getUser"
    GET_PASSWORD = "getPassword"
    GET_EXECUTION_ID = "getExecutionId"
    SET_EXECUTION_ID = "setExecutionId"
    GET_DEVICE_ID = "getDeviceId"
    NO_DEVICE = "No device"
    ECLIPSE_EXECUTION_ID = "eclipseExecutionId"
    _socket = None
    _out = None
    _in = None

    def connect(self, cmd):
        try:
            self._out = cmd + '\n'
            self._socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self._socket.connect((self.LOCALHOST, self.PORT))
            self._socket.sendall(self._out.encode(encoding='utf_8', errors='strict'))
            self._in = self._socket.recv(1024).decode('utf_8').strip('\r\n')
            self.close()
        except socket.herror as e:
            print("Can't connect to MobileCloud for Eclipse plugin - unknown host: " + self.LOCALHOST, e)
            raise e
        except socket.error as e:
            print(
                "Failed to connect to MobileCloud for Eclipse plugin on host: " + self.LOCALHOST + " and port: " + str(
                        self.PORT) + ". Possible reasons are: The code doesn't run in Eclipse or the user doesn't have "
                                     "ECLIPSE role or the MobileCloud recording view is not open." +
                " In these cases use the MobileDriver constructor that receives the host, user and password.",
                e)
            raise e
        return self._in

    def close(self):
        try:
            self._socket.close()
        except Exception as e:
            print ("Failed to close socket")
            print(e)
            raise e

    def get_execution_id(self):
        execution_id = self.connect(self.GET_EXECUTION_ID)
        print("Interactive connector session execution Id: " + execution_id)
        return execution_id

    def get_device_id(self):
        device_id = self.connect(self.GET_DEVICE_ID)
        print("Received eclipse device id: " + device_id)
        if self.NO_DEVICE == device_id:
            device_id = None
        return device_id

    def get_host(self):
        host = self.connect(self.GET_HOST)
        print("Received eclipse host: " + host)
        return host

    def get_user(self):
        user = self.connect(self.GET_USER)
        print("Received eclipse user: " + user)
        return user

    def get_password(self):
        password = self.connect(self.GET_PASSWORD)
        if password == '':
            msg = "Received empty eclipse password."
        else:
            msg = "Received eclipse password."
        print(msg)
        return password

    def set_execution_id(self, execution_id):
        self.connect(self.SET_EXECUTION_ID + " " + execution_id)
        print("Set execution id: " + execution_id)

    @classmethod
    def main(cls, args):
        client_socket = EclipseConnector()
        client_socket.get_execution_id()
        client_socket.get_host()
        client_socket.get_user()
        client_socket.get_password()
        client_socket.get_execution_id()
        client_socket.close()
        print("done")


if __name__ == '__main__':
    import sys

    EclipseConnector.main(sys.argv)
