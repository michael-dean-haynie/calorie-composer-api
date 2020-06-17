function fn() {
    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);

    var config = {
        baseUrl : 'http://localhost:8080'
    };

    // prepare schemas
    config.schemas = karate.call('classpath:schemas.feature', config).schemas;

    // prepare utilities
    config.utils = karate.call('classpath:callable/utilities/utilities.feature', config).utils;

    return config;
}