package org.gene.d2

import com.bftcom.ice.common.maps.DataMapF
import com.bftcom.ice.common.maps.dataMapToString
import com.bftcom.ice.web.antd.InputNumber
import com.bftcom.ice.web.antd.button
import com.bftcom.ice.web.antd.col
import com.bftcom.ice.web.antd.row
import com.bftcom.ice.web.components.field.fieldGroup
import com.bftcom.ice.web.components.form.AbstractEditForm
import com.bftcom.ice.web.components.form.FormProps
import com.bftcom.ice.web.components.form.FormState
import com.bftcom.ice.web.css.css
import com.bftcom.ice.web.dm.JsDataService
import com.bftcom.ice.web.dm.buildDataMapFromJson
import com.bftcom.ice.web.utils.async
import com.bftcom.ice.web.utils.finally
import kotlinx.html.style
import org.gene.Experiment2Service
import org.gene.view.GridState
import react.RBuilder
import react.dom.div
import react.dom.label
import react.updateState


class D2BoardPage(props: FormProps) : AbstractEditForm<FormProps, D2BoardPage.State>(props) {

    interface State : FormState {
        var loading: Boolean?
        var gridState: DataMapF<GridState>?
    }


    override fun componentDidMount() {
        super.componentDidMount()
        exec(Experiment2Service::getGridStateForView.name)
    }

    private fun exec(functionName: String, vararg args: Any?) {

        updateState {
            loading = true
        }
        async {
            JsDataService.remoteCall(
                    Experiment2Service::class.simpleName!!,
                    functionName, *args)
        }.then {
            val dm = buildDataMapFromJson(it.toString())
            if (dm != null)
                println(dm.dataMapToString())
            updateState {
                gridState = dm as? DataMapF<GridState>
            }
        }.finally {
            updateState { loading = false }
        }

    }

    var N: Number = 5
    override fun RBuilder.render() {
        row {
            col(14) {
                child(GridComponent::class) {
                    attrs {
                        this.gridState = state.gridState
                    }
                }
            }

            col(6) {
                fieldGroup("Управление") {
                    div {

                        InputNumber {
                            attrs {
                                defaultValue = N
                                onChange = { N = it }
                            }
                        }
                        label { +"   Кол-во звеньев" }
                    }
                    div {
                        attrs {
                            style = css {
                                paddingTop = "20px"
                            }
                        }
                        button("Новый эксперимент",
                                onClick = { exec(Experiment2Service::newExperimentAndState.name, 16, N) })
                    }
                    div {
                        attrs {
                            style = css {
                                paddingTop = "10px"
                            }
                        }
                        button("1 успешный ход",
                                disabled = state.gridState == null,
                                onClick = { exec(Experiment2Service::randomMove.name) })
                    }

                    div {
                        attrs {
                            style = css {
                                paddingTop = "50px"
                            }
                        }
                        button("Очистить",
                                disabled = state.gridState == null,
                                onClick = { exec(Experiment2Service::clear.name) })
                    }
                }

            }
            col(4) {

            }

        }

    }
}