import SwiftUI
import shared

struct NewsListView: View{
    @ObservedObject var model = NewsListModel()
    
    var body: some View {
        NavigationView {
            List(model.items, id: \.self) { item in
                NavigationLink(destination: NewsItemView(item: item)) {
                    NewsItemRow(item: item)
                }
            }.onAppear{
                model.loadNews()
            }.navigationBarTitle("News", displayMode: .inline)
        }
    }
}
