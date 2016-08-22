from PerfectoLabUtils import PerfectoLabUtils
import json


class WindTunnelUtils:
    def __init__(self):
        pass

    PROPERTIES = "properties"
    SETTINGS = "settings"
    DEVICE = "device"

    NAME = "name"
    DESCRIPTION = "description"
    IMAGE = "image"

    SINGLE_TEST_REPORT_URL_CAPABILITY = "singleTestReportUrl"

    WIND_TUNNEL_PERSONA_CAPABILITY = "windTunnelPersona"
    WIND_TUNNEL_LOCATION_CAPABILITY = "windTunnelLocation"
    WIND_TUNNEL_LOCATION_ADDRESS_CAPABILITY = "windTunnelLocationAddress"
    WIND_TUNNEL_ORIENTATION_CAPABILITY = "windTunnelOrientation"
    WIND_TUNNEL_VNETWORK_CAPABILITY = "windTunnelVNetwork"
    WIND_TUNNEL_BACKGROUND_RUNNING_APPS_CAPABILITY = "windTunnelBackgroundRunningApps"
    #WIND_TUNNEL_REPORT_URL_CAPABILITY is deprecated, use SINGLE_TEST_REPORT_URL_CAPABILITY instead
    WIND_TUNNEL_REPORT_URL_CAPABILITY = "windTunnelReportUrl"
    WIND_TUNNEL_PERSONA_KEY_CAPABILITY = "windTunnelPersonaKey"

    DEVICE_NAME_CAPABILITY = "deviceName"
    DEVICE_PLATFORM_NAME_CAPABILITY = "platformName"
    DEVICE_PLATFORM_VERSION_CAPABILITY = "platformVersion"
    DEVICE_MODEL_CAPABILITY = "model"
    DEVICE_MANUFACTURER_CAPABILITY = "manufacturer"
    DEVICE_NETWORK_CAPABILITY = "network"
    DEVICE_LOCATION_CAPABILITY = "location"
    DEVICE_RESOLUTION_CAPABILITY = "resolution"
    DEVICE_DESCRIPTION_CAPABILITY = "description"

    MOBILE_STATUS_EVENT_COMMAND = "mobile:status:event"
    MOBILE_STATUS_TIMER_COMMAND = "mobile:status:timer"

    POI_DESCRIPTION = "description"
    POI_STATUS = "status"

    SUCCESS = "success"
    FAILURE = "failure"

    REPORT_TIMER_RESULT = "result"
    REPORT_TIMER_THRESHOLD = "threshold"
    REPORT_TIMER_DESCRIPTION = "description"
    REPORT_TIMER_NAME = "name"

    GEORGIA = "Georgia"
    ROSS = "Ross"
    PETER = "Peter"
    SAM = "Sam"
    SARA = "Sara"
    EMPTY = "Empty"

    # Adds a point of interest to the Wind Tunnel report.
    # Example:
    #   point_of_interest(driver, "Login Successful", WindTunnelUtils.SUCCESS)
    @classmethod
    def point_of_interest(cls, driver, name, status):
        params = {cls.POI_DESCRIPTION: name,
                  cls.POI_STATUS: status}
        response_status = driver.execute_script(cls.MOBILE_STATUS_EVENT_COMMAND, params)
        return response_status

    # Adds a timer report to the Wind Tunnel report.
    # Example:
    #   report_timer(driver, loginScreenTimer, 5000, "Timer for login screen", "")
    @classmethod
    def report_timer(cls, driver, result, threshold, description, name):
        params = {cls.REPORT_TIMER_RESULT: result,
                  cls.REPORT_TIMER_THRESHOLD: threshold,
                  cls.REPORT_TIMER_DESCRIPTION: description,
                  cls.REPORT_TIMER_NAME: name}
        status = driver.execute_script(cls.MOBILE_STATUS_TIMER_COMMAND, params)
        return status

    # Example:
    #   properties = WindTunnelUtils.PersonaProperties("Pedro", "This is Pedro's profile", "PUBLIC:personas/Perdo.jpg")
    #   device = WindTunnelUtils.PersonaDevice()
    #   device._model = "iPhone-5S"
    #   settings = WindTunnelUtils.PersonaSettings(Boston", "landscape", "4G LTE Advanced Good", "Waze,YouTube")
    #   persona = WindTunnelUtils.create_wind_tunnel_persona(properties, device, settings)
    @classmethod
    def create_wind_tunnel_persona(cls, properties, device, settings):
        properties_dict = cls.create_properties(properties)
        device_dict = cls.create_device(device)
        settings_dict = cls.create_settings(settings)

        persona = {cls.PROPERTIES: properties_dict,
                   cls.DEVICE: device_dict,
                   cls.SETTINGS: settings_dict}

        json_obj = cls.convert_to_json(persona)
        return json_obj

    # Example:
    #   properties = WindTunnelUtils.PersonaProperties("Pedro", "This is Pedro's profile", "PUBLIC:personas/Perdo.jpg")
    #   device = WindTunnelUtils.PersonaDevice()
    #   device.model = ("iPhone-5S")
    #   settings = WindTunnelUtils.PersonaSettings("Boston", "landscape", "4G LTE Advanced Good", "Waze,YouTube")
    #   repositoryKey = WindTunnelUtils.upload_wind_tunnel_persona(myHost, myUser, myPassword, "PRIVATE:Personas",
    #                                                              properties, device, settings)
    #   capabilities[WindTunnelUtils.WIND_TUNNEL_PERSONA_KEY_CAPABILITY] = repositoryKey
    @classmethod
    def upload_wind_tunnel_persona(cls, host, user, password, repository_folder, properties, device, settings):
        if repository_folder is None:
            raise RuntimeError("Can't upload persona without repository folder")
        persona = cls.create_wind_tunnel_persona(properties, device, settings)
        repository_key = repository_folder + "/" + properties.name + ".json"
        PerfectoLabUtils.upload_media(host, user, password, persona.encode(), repository_key)
        return repository_key

    class PersonaProperties:
        def __init__(self, _name, _description, _image):
            self.name = _name
            self.description = _description
            self.image = _image

    class PersonaSettings:
        def __init__(self, _location_address, _orientation, _vnetwork_profile, _applications, _location=None):
            self.location_address = _location_address
            self.orientation = _orientation
            self.vnetwork_profile = _vnetwork_profile
            self.applications = _applications
            self.location = _location

    class PersonaDevice:
        def __init__(self, _device_name=None, _platform_name=None, _platform_version=None, _manufacturer=None,
                     _model=None, _resolution=None, _network=None, _location=None, _description=None):
            self.device_name = _device_name
            self.platform_name = _platform_name
            self.platform_version = _platform_version
            self.manufacturer = _manufacturer
            self.model = _model
            self.resolution = _resolution
            self.network = _network
            self.location = _location
            self.description = _description

    @classmethod
    def convert_to_json(cls, content):
        return json.dumps(content)

    @classmethod
    def create_properties(cls, properties):
        if properties is None:
            raise ValueError("Can't create persona without properties")
        if properties.name is None:
            raise ValueError("Can't create persona without name")
        properties_dict = {}
        properties_dict[cls.NAME] = properties.name
        if properties.description is not None:
            properties_dict[cls.DESCRIPTION] = properties.description
        if properties.image is not None:
            properties_dict[cls.IMAGE] = properties.image
        return properties_dict

    @classmethod
    def create_settings(cls, settings):
        settings_dict = {}
        if settings is not None:
            if settings.location is not None:
                settings_dict[cls.WIND_TUNNEL_LOCATION_CAPABILITY] = settings.location
            if settings.location_address is not None:
                settings_dict[cls.WIND_TUNNEL_LOCATION_ADDRESS_CAPABILITY] = settings.location_address
            if settings.orientation is not None:
                settings_dict[cls.WIND_TUNNEL_ORIENTATION_CAPABILITY] = settings.orientation
            if settings.vnetwork_profile is not None:
                settings_dict[cls.WIND_TUNNEL_VNETWORK_CAPABILITY] = settings.vnetwork_profile
            if settings.applications is not None:
                settings_dict[cls.WIND_TUNNEL_BACKGROUND_RUNNING_APPS_CAPABILITY] = settings.applications
        return settings_dict

    @classmethod
    def create_device(cls, device):
        device_dict = {}
        if device is not None:
            if device.device_name is not None:
                device_dict[cls.DEVICE_NAME_CAPABILITY] = device.device_name
            if device.platform_name is not None:
                device_dict[cls.DEVICE_PLATFORM_NAME_CAPABILITY] = device.platform_name
            if device.platform_version is not None:
                device_dict[cls.DEVICE_PLATFORM_VERSION_CAPABILITY] = device.platform_version
            if device.model is not None:
                device_dict[cls.DEVICE_MODEL_CAPABILITY] = device.model
            if device.manufacturer is not None:
                device_dict[cls.DEVICE_MANUFACTURER_CAPABILITY] = device.manufacturer
            if device.network is not None:
                device_dict[cls.DEVICE_NETWORK_CAPABILITY] = device.network
            if device.location is not None:
                device_dict[cls.DEVICE_LOCATION_CAPABILITY] = device.location
            if device.resolution is not None:
                device_dict[cls.DEVICE_RESOLUTION_CAPABILITY] = device.resolution
            if device.description is not None:
                device_dict[cls.DEVICE_DESCRIPTION_CAPABILITY] = device.description
        return device_dict
