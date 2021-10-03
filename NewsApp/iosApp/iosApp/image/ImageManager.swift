//
//  ImageStorage.swift
//  TestNewsSearch
//
//  Created by 1 on 22.02.2019.
//  Copyright © 2019 1. All rights reserved.
//

import Foundation
import SDWebImage

// MARK: Image manager
class ImageManager {
    static let sharedInstance = ImageManager()
    private init() {
        //SDWebImageDownloader.shared.maxConcurrentDownloads = 6
        //SDWebImageDownloader.shared.shouldDecompressImages = false
        
    }
    
    func setImage(toImageView: UIImageView?, forKey: String) {
        if let image = getImageByKey(forKey) {
            toImageView?.image = image
        } else {
            weak var distanceImageView = toImageView
            SDWebImageDownloader.shared.downloadImage(with: URL(string: forKey), options: SDWebImageDownloaderOptions.lowPriority, progress: nil) { [weak self]
                (image, error, cacheType, imageURL) in
                guard let image = image else {
                    return
                }
                distanceImageView?.image = image
                self?.saveImageWithKey(image.pngData(), forKey)
            }
        }
    }
    
    func receiveImage(forKey: String, action:@escaping(UIImage)->Void) {
        if let image = getImageByKey(forKey) {
            action(image)
        } else {
            SDWebImageDownloader.shared.downloadImage(with: URL(string: forKey), options: SDWebImageDownloaderOptions.lowPriority, progress: nil) { [weak self]
                (image, error, cacheType, imageURL) in
                guard let image = image else {
                    return
                }
                action(image)
                self?.saveImageWithKey(image.pngData(), forKey)
            }
        }
    }
    
    private func saveImageWithKey(_ imageData: Data?, _ imageKey: String) {
        SDImageCache.shared.storeImageData(toDisk: imageData, forKey: imageKey)
    }
    
    private func getImageByKey(_ imageKey: String) -> UIImage? {
        return SDImageCache.shared.imageFromDiskCache(forKey: imageKey)
    }
    
    func cleanDisk() {
        SDImageCache.shared.clearDisk {
            
        }
    }
    deinit {
        SDImageCache.shared.clearMemory()
    }
}

