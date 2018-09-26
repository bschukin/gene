package org.gene.d2

import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.common.maps.Field
import com.bftcom.ice.common.maps.dataMapToString
import com.bftcom.ice.web.antd.*
import com.bftcom.ice.web.components.form.AbstractEditForm
import com.bftcom.ice.web.components.form.FormProps
import com.bftcom.ice.web.components.form.FormState
import com.bftcom.ice.web.css.css
import com.bftcom.ice.web.dm.JsDataService
import com.bftcom.ice.web.dm.buildDataMapFromJson
import com.bftcom.ice.web.utils.async
import com.bftcom.ice.web.utils.finally
import kotlinx.html.InputType
import kotlinx.html.style
import org.gene.Charges
import org.gene.Experiment2Service
import org.gene.view.CellState
import org.gene.view.GridState
import org.gene.view.LinkState
import react.RBuilder
import react.dom.*
import react.updateState
import react.updateStateAsync


class D2BoardPage(props: FormProps) : AbstractEditForm<FormProps, D2BoardPage.State>(props) {

    interface State : FormState {
        var loading: Boolean?
        var gridState: DataMapF<GridState>?
    }


    override fun componentDidMount() {
        super.componentDidMount()
        fetchStateFromServer()
    }

    private fun fetchStateFromServer() {

        updateState {
            loading = true
        }
        async {
            JsDataService.remoteCall(
                    Experiment2Service::class.simpleName!!,
                    Experiment2Service::newExperimentAndState.name)
        }.then {
            val dm = buildDataMapFromJson(it.toString())
            updateState {
                state.gridState = dm as DataMapF<GridState>
            }
        }.finally {
            updateState { loading = false }
        }

    }

    override fun RBuilder.render() {
        table {
            attrs {
                style = css {
                    margin = "30px"
                }
            }
            tr {
                td {
                    child(GridComponent::class) {
                        attrs {
                            this.gridState = state.gridState
                        }
                    }
                }
                td {
                    div {
                        attrs {
                            style = css {
                                margin = "30px"
                            }
                        }
                        div {
                            label { +"Звеньев: " }
                            InputNumber {

                            }
                        }
                        button("Нажми мене")
                    }
                }
            }
        }

    }


}