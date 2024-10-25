//
//  RestApi.swift
//  SpeedReporter
//
//  Created by Macbookpro22506 on 25/10/24.
//

import Foundation

class RestApi {
    
    static let baseApi: String = "http://10.53.193.25:8080/"
    
    struct EndPoint {
        static let GetThreshold: String = baseApi + "getSpeedThreshold"
        static let SetSpeedThreshold: String = baseApi + "setSpeedThreshold"
        static let ReportSpeedViolation: String = baseApi + "reportSpeedViolation"
        static let GetSpeedViolation: String = baseApi + "getSpeedViolation"
    }
}
