//
//  Storage.swift
//  SpeedReporter
//
//  Created by Macbookpro22506 on 24/10/24.
//

import Foundation
import UIKit

class Storage: NSObject {

    static func setData<T>(key: String, value: T) {
        UserDefaults.standard.set(value, forKey: key)
        UserDefaults.standard.synchronize()
    }
    
    static func getData<T>(key: String) -> T? {
        return UserDefaults.standard.object(forKey: key) as? T
    }
    
    static func setBoolData(key: String, value: Bool) {
        UserDefaults.standard.set(value, forKey: key)
        UserDefaults.standard.synchronize()
    }
    
    static func getBoolData(key: String) -> Bool {
        return UserDefaults.standard.bool(forKey: key)
    }
}

struct User {
    static let vin: String = "VehicleNumber"
    static let isRegistered: String = "isRegistered"
}
