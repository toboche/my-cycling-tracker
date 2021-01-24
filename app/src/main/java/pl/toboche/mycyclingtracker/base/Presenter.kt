package pl.toboche.mycyclingtracker.base

abstract class Presenter<View> {
    var view: View? = null

    open fun attach(view: View) {
        this.view = view
    }

    open fun detach() {
        view = null
    }
}