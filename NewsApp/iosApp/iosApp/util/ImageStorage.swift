//
//  ImageStorage.swift
//  iosApp
//
//  Created by 1 on 02.06.2020.
//

import Foundation
import SDWebImage
import UIKit

final class ImageStorage {
    
    static let sharedInstance = ImageStorage()
    private init() {
        
    }
    
    func setupImage(imageKey: String,imageView: UIImageView?){
        if let image = getImageByKey(imageKey){
            imageView?.image = image
        } else {
            loadImage(imageKey: imageKey, imageView: imageView)
        }
    }
    
    func loadImage(imageKey: String,imageView: UIImageView?){
        SDWebImageDownloader.shared.downloadImage(with: URL(string: imageKey)) { (image, data, error, finished) in
            if let image = image {
                image.resize(newSize: CGSize(width: 512,height: 512), completion: {[weak self] (im, imagedata) in
                    imageView?.image = nil 
                    self?.saveImageWithKey(imagedata, imageKey)
                    imageView?.image = im
                })
                
            }
        }
    }
    
    func saveToGallery(link: String) {
        SDWebImageDownloader.shared.downloadImage(with: URL(string:link)) { (image, data, error, finished) in
            if let image = image {
                image.resize(newSize: CGSize(width: 512,height: 512), completion: {[weak self] (im, imagedata) in
                      UIImageWriteToSavedPhotosAlbum(im, self, nil, nil)
                })
            }
        }
        
    }
    
    func saveImageWithKey(_ imageData: Data?, _ imageKey: String) {
        SDImageCache.shared.removeImage(forKey: imageKey, fromDisk: true) {
            if let data = imageData, let image = UIImage(data: data) {
                image.resize(newSize: CGSize(width: 512,height: 512), completion: { (im, imagedata) in
                    SDImageCache.shared.storeImageData(toDisk: imagedata, forKey: imageKey)
                })
            }
        }
        
    }
    
    func cleanImage(_ imageKey: String) {
        SDImageCache.shared.removeImage(forKey: imageKey, fromDisk: true, withCompletion: nil)
    }
    
    
    func getImageByKey(_ imageKey: String) -> UIImage? {
        return SDImageCache.shared.imageFromDiskCache(forKey: imageKey)
        
    }
    
    func getImageData(imageKey: String)->Data? {
        return getImageByKey(imageKey)?.toData()
    }
    
    func cleanDisk() {
        SDImageCache.shared.clearDisk {
            
        }
    }
    deinit {
        SDImageCache.shared.clearMemory()
        
    }
    
}

extension UIImage {
    func resize(newSize: CGSize,completion: @escaping((UIImage,Data?)->Void)){
        DispatchQueue.global(qos: .userInitiated).async {
            
            let widthScale = newSize.width / self.size.width
            let heightScale = newSize.height / self.size.height
            
            if (widthScale > 1 || heightScale > 1){
                DispatchQueue.main.async {
                    completion(self.copy() as! UIImage,self.jpegData(compressionQuality: 1))
                }
                return
            }
            
            let scaleFactor = widthScale < heightScale ? widthScale : heightScale
            let scaledSize = CGSize(width: self.size.width * scaleFactor, height: self.size.height * scaleFactor)
            
            let rect = CGRect(x: 0, y: 0, width: scaledSize.width, height: scaledSize.height)
            UIGraphicsBeginImageContextWithOptions(scaledSize, false, 1.0)
            self.draw(in: rect)
            if let newImage = UIGraphicsGetImageFromCurrentImageContext() {
                UIGraphicsEndImageContext()
                let imageData = newImage.jpegData(compressionQuality: 0.5)
                DispatchQueue.main.async {
                    completion(newImage,imageData)
                }
            }
        }
        
        
    }
    
    func toData(_ compression: CGFloat = 1.0)->Data? {
        return self.jpegData(compressionQuality: compression)
    }
}
