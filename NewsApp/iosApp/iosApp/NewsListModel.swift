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

class NewsListModel : ObservableObject {
    @Published var items: [NewsItem] = [NewsItem]()
    
    lazy var store: NewsListStore = {
        let store = NewsListStore()
        store.observeState().collect(collector: self.collector, completionHandler: {_,_ in })
        return store
    }()
    
    private lazy var collector: Observer = {
        let collector = Observer {value in
            if let value = value as? NewsStoreState {
                self.processNews(data: value.news)
            }
        }
        return collector
    }()
    
    func loadNews() {
        store.refresh(forceLoad: true)
    }
    
    func processNews(data: [NewsItem]) {
        self.items = [NewsItem]()
        self.items.append(contentsOf: data)
    }
}

typealias Collector = Kotlinx_coroutines_coreFlowCollector

class Observer: Collector {
    let callback:(Any?) -> Void
    
    init(callback: @escaping (Any?) -> Void) {
        self.callback = callback
    }
    
    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        callback(value)
        completionHandler(KotlinUnit(), nil)
    }
}
