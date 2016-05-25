import os
import base64
import urllib
import urllib2

from EclipseConnector import EclipseConnector


class PerfectoLabUtils:
    HTTPS = "https://"
    MEDIA_REPOSITORY = "/services/repositories/media/"
    UPLOAD = "upload"
    OVERWRITE = "overwrite"
    OPERATION = "operation"
    USER = "user"
    PASSWORD = "password"

    def __init__(self):
        pass

    # Download the report.
    #
    # type - pdf, html, csv, xml
    # Example: download_report(driver, 'pdf', '/test/report');
    @classmethod
    def download_report(cls, driver, file_type, file_path):
        try:
            report = driver.execute_script('mobile:report:download', {'type': file_type})

            cls.save_to_file(report, file_path + "." + file_type)

        except Exception as e:
            print('Report download failed with error:', e)

    # Call this method if you want the script to share the devices with the Perfecto Lab plugin.
    @staticmethod
    def set_execution_id_capability(capabilities, host):
        connector = EclipseConnector()
        eclipse_host = connector.get_host()
        if eclipse_host is None or eclipse_host.lower() == host.lower():
            execution_id = connector.get_execution_id()
            capabilities[EclipseConnector.ECLIPSE_EXECUTION_ID] = execution_id

    # Download all the report attachments with a certain type.
    # type - video, image, vital, network
    #
    # Examples:
    #   download_attachment(driver, "video", "C:\\test\\report\\video", "flv");
    #   download_attachment(driver, "image", "C:\\test\\report\\images", "jpg");
    @classmethod
    def download_attachment(cls, driver, file_type, file_name, file_extension):
        try:
            done = False
            index = 0

            while not done:
                attachment = driver.execute_script('mobile:report:attachment', {'type': file_type, 'index': index})
                if attachment is None:
                    done = True
                else:
                    index += 1
                    cls.save_to_file(attachment, file_name + '_' + str(index) + '.' + file_extension)

        except Exception as e:
            print('Attachment download failed with error:')
            print(e)

    # Uploads a file to the media repository.
    #
    # Example:
    #   upload_media_from_file("demo.perfectomobile.com", "john@perfectomobile.com", "123456", "/test/ApiDemos.apk",
    #                        "PRIVATE:apps/ApiDemos.apk");
    @classmethod
    def upload_media_from_file(cls, host, user, password, file_path, repository_key):
        new_file = open(file_path, 'rb')
        content = new_file.read()
        new_file.close()
        cls.upload_media(host, user, password, content, repository_key)

    # Uploads a url item to the media repository.
    #
    # Example:
    #   url = "http://file.appsapk.com/wp-content/uploads/downloads/Sudoku%20Free.apk"
    #   upload_media_from_url("demo.perfectomobile.com", "john@perfectomobile.com", "123456", url,
    #                         "PRIVATE:apps/ApiDemos.apk")
    @classmethod
    def upload_media_from_url(cls, host, user, password, url, repository_key):
        content = urllib2.urlopen(url).read()
        cls.upload_media(host, user, password, content, repository_key)

    # Uploads content to the media repository.
    #
    # Example:
    #   upload_media("demo.perfectomobile.com", "john@perfectomobile.com", "123456", content,
    #                "PRIVATE:apps/ApiDemos.apk")
    @classmethod
    def upload_media(cls, host, user, password, content, repository_key):
        if content is not None:
            url = cls.get_post_request_url(host, user, password, repository_key, cls.UPLOAD)
            cls.send_request(url, content)

    @classmethod
    def get_post_request_url(cls, host, user, password, repository_key, operation, overwrite=True):
        url = cls.HTTPS + host + cls.MEDIA_REPOSITORY
        if repository_key != "":
            url += "/" + repository_key
        params = {cls.OPERATION: operation,cls.USER: user, cls.PASSWORD: password}
        if overwrite:
            params[cls.OVERWRITE] = True
        query = urllib.urlencode(params)
        url += "?" + query
        return url

    @classmethod
    def send_request(cls, url, content):
        print("Submitting", url)
        try:
            if content == "":
                response = urllib2.urlopen(url)
            else:
                response = urllib2.urlopen(url, content)
        except urllib2.URLError as e:
            if hasattr(e, 'reason'):
                print 'Request to url: {0} failed with the following reason: {1}'.format(url,e.reason)
            elif hasattr(e, 'code'):
                print 'Request to url: {0} failed with the following code: {1}'.format(url,e.code)
        else:  # response.getcode() == 200
            return response

    @classmethod
    def save_to_file(cls, source, file_full_path, encoding='ascii'):
        new_file = None
        try:
            file_dir = os.path.dirname(os.path.abspath(file_full_path))
            new_file = base64.b64decode(source.encode(encoding))
            if not os.path.exists(file_dir):
                os.makedirs(file_dir)
            with open(file_full_path, 'wb') as f:
                f.write(new_file)
            print('File was downloaded to ' + file_full_path)
        except (NameError, IOError, AttributeError) as e:
            print('Failed to download file to ' + file_full_path)
            print('Error details: ' + str(e))
        finally:
            if new_file is not None:
                del new_file
