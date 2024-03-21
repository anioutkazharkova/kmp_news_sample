//
//  NewsListModel.swift
//  iosApp
//
//  Created by Anna Zharkova on 03.10.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class NewsListModel : ObservableObject, INewsListView {
    @Published var items: [NewsItem] = [NewsItem]()
    
    private lazy var viewModel: NewsViewModel = {
        let vm =  NewsViewModel()
        vm.newsItems.collect(collector: self.collector) { _ in
            
        }
        return vm
    }()
    
    private lazy var collector: Observer = {
        let collect = Observer { value in
            if let items = value as? [NewsItem] {
                self.processNews(data: items)
            }
        }
        return collect
}()
    
    
    func setupNews(data: NewsList) {
        self.items = data.articles
    }
    
    func loadNews() {
        viewModel.loadNews()
    }
    
    func processNews(data: [NewsItem]) {
        self.items = [NewsItem]()
       self.items.append(contentsOf: data)
    }
}


typealias Collector = Kotlinx_coroutines_coreFlowCollector

class Observer: Collector {
    func emit(value: Any?, completionHandler: @escaping ((any Error)?) -> Void) {
        callback(value)
        completionHandler(nil)
    }
    
    let callback:(Any?) -> Void
    
    init(callback: @escaping (Any?) -> Void) {
        self.callback = callback
    }
}

